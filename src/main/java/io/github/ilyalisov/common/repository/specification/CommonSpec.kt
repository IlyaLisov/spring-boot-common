package io.github.ilyalisov.common.repository.specification

import io.github.ilyalisov.common.model.BaseEntity
import io.github.ilyalisov.common.model.Status
import modifyFTS
import org.springframework.data.jpa.domain.Specification
import java.util.*

/**
 * Utility object providing common JPA Specifications for filtering entities
 * based on status, authorship, and temporal criteria. These specifications
 * can be combined to build complex queries in a type-safe and reusable
 * manner.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @see Specification
 * @see BaseEntity
 * @see Status
 */
object CommonSpec {

    /**
     * Creates a specification to filter entities by their status.
     *
     * @param T the type of entity extending BaseEntity
     * @param status the status to filter by
     * @return Specification that matches entities with the given status
     */
    fun <T : BaseEntity> hasStatus(
        status: Status
    ): Specification<T> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(
                root.get<Status>("status"),
                status
            )
        }
    }

    /**
     * Creates a specification to filter active entities.
     * Equivalent to [hasStatus] with [Status.ACTIVE].
     *
     * @param T the type of entity extending BaseEntity
     * @return Specification that matches active entities
     */
    fun <T : BaseEntity> isActive(): Specification<T> {
        return hasStatus(Status.ACTIVE)
    }

    /**
     * Creates a specification to filter not active entities.
     * Equivalent to [hasStatus] with [Status.NOT_ACTIVE].
     *
     * @param T the type of entity extending BaseEntity
     * @return Specification that matches not active entities
     */
    fun <T : BaseEntity> isNotActive(): Specification<T> {
        return hasStatus(Status.NOT_ACTIVE)
    }

    /**
     * Creates a specification to filter blocked entities.
     * Equivalent to [hasStatus] with [Status.BLOCKED].
     *
     * @param T the type of entity extending BaseEntity
     * @return Specification that matches blocked entities
     */
    fun <T : BaseEntity> isBlocked(): Specification<T> {
        return hasStatus(Status.BLOCKED)
    }

    /**
     * Creates a specification to filter deleted entities.
     * Equivalent to [hasStatus] with [Status.DELETED].
     *
     * @param T the type of entity extending BaseEntity
     * @return Specification that matches deleted entities
     */
    fun <T : BaseEntity> isDeleted(): Specification<T> {
        return hasStatus(Status.DELETED)
    }

    /**
     * Creates a specification to filter entities under moderation.
     * Equivalent to [hasStatus] with [Status.UNDER_MODERATION].
     *
     * @param T the type of entity extending BaseEntity
     * @return Specification that matches entities under moderation
     */
    fun <T : BaseEntity> isUnderModeration(): Specification<T> {
        return hasStatus(Status.UNDER_MODERATION)
    }

    /**
     * Creates a specification to filter moderation declined entities.
     * Equivalent to [hasStatus] with [Status.MODERATION_DECLINED].
     *
     * @param T the type of entity extending BaseEntity
     * @return Specification that matches moderation declined entities
     */
    fun <T : BaseEntity> isModerationDeclined(): Specification<T> {
        return hasStatus(Status.MODERATION_DECLINED)
    }

    /**
     * Creates a specification to filter draft entities.
     * Equivalent to [hasStatus] with [Status.DRAFT].
     *
     * @param T the type of entity extending BaseEntity
     * @return Specification that matches draft entities
     */
    fun <T : BaseEntity> isDraft(): Specification<T> {
        return hasStatus(Status.DRAFT)
    }

    /**
     * Creates a specification to filter entities by their author's ID.
     * Assumes entities have an "author" association with an "id" field.
     *
     * @param T the type of entity extending BaseEntity
     * @param id the author's UUID to filter by
     * @return Specification that matches entities with the given author ID
     */
    fun <T : BaseEntity> hasAuthorId(
        id: UUID?
    ): Specification<T> {
        return Specification { root, _, criteriaBuilder ->
            if (id == null) {
                criteriaBuilder.conjunction();
            }
            criteriaBuilder.equal(
                root.get<Any>("author")
                    .get<UUID>("id"),
                id
            )
        }
    }

    /**
     * Creates a specification to filter entities created within a date
     * range. Supports open-ended ranges when either start or end date is
     * null.
     *
     * @param T the type of entity extending BaseEntity
     * @param D the type of date (must be Comparable)
     * @param startDate the start of the date range (inclusive), can be null
     * @param endDate the end of the date range (inclusive), can be null
     * @return Specification that matches entities created in the period
     */
    fun <T : BaseEntity, D : Comparable<D>> inPeriod(
        startDate: D?,
        endDate: D?
    ): Specification<T> {
        return Specification { root, _, criteriaBuilder ->
            val createdAtPath = root.get<D>("created")
            when {
                startDate != null && endDate != null -> {
                    criteriaBuilder.between(
                        createdAtPath,
                        startDate,
                        endDate
                    )
                }

                startDate != null -> {
                    criteriaBuilder.greaterThanOrEqualTo(
                        createdAtPath,
                        startDate
                    )
                }

                endDate != null -> {
                    criteriaBuilder.lessThanOrEqualTo(
                        createdAtPath,
                        endDate
                    )
                }

                else -> criteriaBuilder.conjunction()
            }
        }
    }

    /**
     * Creates a specification combining active status and date range
     * filtering. Convenience method for common query patterns.
     *
     * @param T the type of entity extending BaseEntity
     * @param D the type of date (must be Comparable)
     * @param start the start of the date range (inclusive), can be null
     * @param end the end of the date range (inclusive), can be null
     * @return Specification that matches active entities in the period
     */
    fun <T : BaseEntity, D : Comparable<D>> activeInPeriod(
        start: D?,
        end: D?
    ): Specification<T> {
        return Specification.allOf(
            isActive(),
            inPeriod(
                start,
                end
            )
        )
    }

    /**
     * Creates a specification for full-text search using PostgreSQL's tsvector
     * functionality. This specification performs text search with ranking and
     * ordering by relevance using the specified dictionary configuration.
     *
     * The specification:
     * - Converts the search query to tsquery format using the specified
     * dictionary
     * - Orders results by relevance using ts_rank_cd function
     * - Filters results using custom tsvector_match function
     * - Returns all results (no filtering) when search query is null or empty
     *
     * @param T the type of entity to search
     * @param searchQuery the search query string to match against. If null or
     * blank, returns a specification that matches all entities (no filtering
     * applied)
     * @param literal the PostgreSQL text search dictionary to use for
     * tokenization and stemming. Defaults to "russian". Common values include:
     *        - "russian" - for Russian language
     *        - "english" - for English language
     *        - "simple" - language-independent simple dictionary
     * @return Specification that performs full-text search with relevance
     * ranking or matches all entities if search query is null/empty
     *
     * @throws IllegalArgumentException if the entity does not have "fts" field
     *         of tsvector type or if the specified dictionary is not available
     *
     * @see modifyFTS
     * @see tsvector_match
     */
    fun <T> containsQuery(
        searchQuery: String?,
        literal: String = "russian"
    ): Specification<T> {
        return Specification { root, query, criteriaBuilder ->
            if (searchQuery.isNullOrBlank()) {
                criteriaBuilder.conjunction()
            }
            val modifiedQuery = modifyFTS(searchQuery)
            val tsquery = criteriaBuilder.function(
                "to_tsquery",
                String::class.java,
                criteriaBuilder.literal(literal),
                criteriaBuilder.literal(modifiedQuery)
            )
            query.orderBy(
                criteriaBuilder.desc(
                    criteriaBuilder.function(
                        "ts_rank_cd",
                        Double::class.java,
                        root.get<Any>("fts"),
                        tsquery
                    )
                )
            )
            criteriaBuilder.isTrue(
                criteriaBuilder.function(
                    "tsvector_match",
                    Boolean::class.java,
                    root.get<Any>("fts"),
                    tsquery
                )
            )
        }
    }

}