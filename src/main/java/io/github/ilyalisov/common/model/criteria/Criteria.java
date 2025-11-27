package io.github.ilyalisov.common.model.criteria;

import io.github.ilyalisov.common.model.Status;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.UUID;

/**
 * Abstract base class for all criteria implementations used for filtering and
 * pagination in data retrieval operations. Provides common pagination, status
 * filtering, and search query capabilities that can be extended for specific
 * domain requirements.
 * <p>
 * This class uses Lombok's {@link SuperBuilder} to provide a fluent builder
 * pattern for creating criteria instances with sensible defaults.
 * </p>
 *
 * @author Ilya Lisov
 * @see Status
 * @see Pageable
 * @see SuperBuilder
 * @since 0.1.0
 */
@Getter
@SuperBuilder
public abstract class Criteria {

    /**
     * The current page number (1-based index). Defaults to 1.
     * Represents which page of results to retrieve.
     */
    protected int pageId;

    /**
     * The number of items to display per page. Defaults to 10.
     * Must be a positive integer.
     */
    protected int perPage;

    /**
     * The status filter for entities. Defaults to {@link Status#ACTIVE}.
     * Used to filter entities based on their status.
     */
    protected Status status;

    /**
     * The UUID of the author/creator of the entity. Used to filter entities
     * based on their authorship. When specified, the criteria will only match
     * entities that were created by the user with this specific ID.
     * <p>
     * This field is typically used in scenarios where users need to view or
     * manage only their own content, or when implementing user-specific
     * data access controls.
     * </p>
     */
    protected UUID authorId;

    /**
     * The search query string for text-based filtering.
     * Can be used for full-text search or partial matching.
     */
    protected String query;

    /**
     * Returns the trimmed search query string.
     * <p>
     * If the query is null, returns an empty string. Otherwise, returns the
     * query with leading and trailing whitespace removed.
     * </p>
     *
     * @return the trimmed query string, never null
     */
    public String getQuery() {
        if (query != null) {
            return query.trim();
        }
        return "";
    }

    /**
     * Abstract builder class for {@link Criteria} implementations.
     * Provides common builder methods with sensible defaults and null-safe
     * operations.
     *
     * @param <C> the concrete criteria type being built
     * @param <B> the concrete builder type
     */
    public abstract static class CriteriaBuilder<
            C extends Criteria,
            B extends CriteriaBuilder<C, B>
            > {

        /**
         * Default constructor that initializes builder with sensible defaults:
         * <ul>
         *   <li>pageId: 1</li>
         *   <li>perPage: 10</li>
         *   <li>status: {@link Status#ACTIVE}</li>
         * </ul>
         */
        public CriteriaBuilder() {
            this.pageId = 1;
            this.perPage = 10;
            this.status = Status.ACTIVE;
        }

        /**
         * Copy constructor that creates a new builder instance initialized with
         * values from an existing criteria object. This is useful for creating
         * modified versions of existing criteria while preserving the original
         * filtering and pagination settings.
         *
         * @param criteria the existing criteria object to copy values from,
         *                 must not be null
         * @throws IllegalArgumentException if the criteria parameter is null
         */
        public CriteriaBuilder(
                final Criteria criteria
        ) {
            Objects.requireNonNull(
                    criteria,
                    "Criteria cannot be null"
            );
            this.pageId = criteria.getPageId();
            this.perPage = criteria.getPerPage();
            this.status = criteria.getStatus();
            this.authorId = criteria.getAuthorId();
            this.query = criteria.getQuery();
        }

        /**
         * Sets the page number for pagination.
         * <p>
         * If the provided pageId is null, defaults to 1.
         * Page numbers are 1-based (page 1 is the first page).
         * </p>
         *
         * @param pageId the page number (1-based), can be null to use default
         * @return the builder instance for method chaining
         * @throws IllegalArgumentException if pageId is less than 1
         */
        public B pageId(
                final Integer pageId
        ) {
            if (pageId != null && pageId < 1) {
                throw new IllegalArgumentException(
                        "pageId must be greater than or equal to 1"
                );
            }
            this.pageId = Objects.requireNonNullElse(
                    pageId,
                    1
            );
            return self();
        }

        /**
         * Sets the number of items per page.
         * <p>
         * If the provided perPage is null, defaults to 10.
         * The value must be positive.
         * </p>
         *
         * @param perPage the number of items per page, can be null to use
         *                default
         * @return the builder instance for method chaining
         * @throws IllegalArgumentException if perPage is less than 1
         */
        public B perPage(
                final Integer perPage
        ) {
            if (perPage != null && perPage < 1) {
                throw new IllegalArgumentException(
                        "perPage must be greater than or equal to 1"
                );
            }
            this.perPage = Objects.requireNonNullElse(
                    perPage,
                    10
            );
            return self();
        }

        /**
         * Sets the status filter for the criteria.
         * <p>
         * If the provided status is null, defaults to {@link Status#ACTIVE}.
         * This filter is used to retrieve entities with specific status values.
         * </p>
         *
         * @param status the status to filter by, can be null to use default
         * @return the builder instance for method chaining
         * @see Status
         */
        public B status(
                final Status status
        ) {
            this.status = Objects.requireNonNullElse(
                    status,
                    Status.ACTIVE
            );
            return self();
        }

        /**
         * Sets the author ID for filtering entities by their creator. When this
         * method is called, the criteria will only return entities that were
         * created by the specified author.
         * <p>
         * This is useful for implementing user-specific views, personal
         * dashboards, or access control scenarios where users should only see
         * their own content.
         * </p>
         *
         * @param authorId the UUID of the author to filter by, can be null to
         *                 clear author filtering
         * @return the builder instance for method chaining
         */
        public B authorId(
                final UUID authorId
        ) {
            this.authorId = authorId;
            return self();
        }

        /**
         * Sets the search query string for text-based filtering.
         * <p>
         * The query can be used for full-text search, partial matching, or
         * any other text-based filtering logic implemented by the repository.
         * </p>
         *
         * @param query the search query string, can be null
         * @return the builder instance for method chaining
         */
        public B query(
                final String query
        ) {
            this.query = query;
            return self();
        }

    }

    /**
     * Creates a {@link Pageable} instance using the criteria's pagination
     * settings without any specific sorting.
     * <p>
     * This is a convenience method that delegates to {@link #getPageable(Sort)}
     * with a null sort parameter.
     * </p>
     *
     * @return a Pageable instance configured with pageId and perPage values
     * @see #getPageable(Sort)
     */
    public Pageable getPageable() {
        return getPageable(Sort.unsorted());
    }

    /**
     * Creates a {@link Pageable} instance using the criteria's pagination
     * settings and the provided sort configuration.
     * <p>
     * Implementing classes must provide concrete implementation to handle
     * domain-specific sorting requirements. The method should convert the
     * 1-based pageId to Spring Data's 0-based page number.
     * </p>
     *
     * @param defaultSort the default sort to apply if no sort is specified
     *                    in the criteria, can be null
     * @return a Pageable instance configured with pagination and sorting
     * @throws IllegalArgumentException if pageId is less than 1 or perPage is
     *                                  less than 1
     */
    public abstract Pageable getPageable(
            Sort defaultSort
    );

    /**
     * Converts this criteria instance into a JPA {@link Specification} for the
     * given entity class.
     * <p>
     * This method transforms the filtering criteria (status, authorId, query,
     * etc.) into a type-safe JPA Specification that can be used with Spring
     * Data JPA repositories. Implementing classes should provide concrete
     * implementations that handle domain-specific filtering logic.
     * </p>
     *
     * @param <T>   the type of entity for which the specification is created
     * @param clazz the class object representing the entity type,
     *              must not be null
     * @return a {@link Specification} instance that encapsulates all filtering
     * conditions defined in this criteria
     * @throws IllegalArgumentException      if entityClass is null
     * @throws UnsupportedOperationException if not overridden in child class
     * @see Specification
     */
    public <T> Specification<T> toSpec(
            final Class<T> clazz
    ) {
        throw new UnsupportedOperationException(
                "You need to implement this method."
        );
    }

}
