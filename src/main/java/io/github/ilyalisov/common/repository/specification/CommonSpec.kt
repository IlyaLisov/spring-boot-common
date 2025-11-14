package io.github.ilyalisov.common.repository.specification

import io.github.ilyalisov.common.model.BaseEntity
import io.github.ilyalisov.common.model.Status
import org.springframework.data.jpa.domain.Specification
import java.util.*

object CommonSpec {

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

    fun <T : BaseEntity> isActive(): Specification<T> {
        return hasStatus(Status.ACTIVE)
    }

    fun <T : BaseEntity> isNotActive(): Specification<T> {
        return hasStatus(Status.NOT_ACTIVE)
    }

    fun <T : BaseEntity> isBlocked(): Specification<T> {
        return hasStatus(Status.BLOCKED)
    }

    fun <T : BaseEntity> isDeleted(): Specification<T> {
        return hasStatus(Status.DELETED)
    }

    fun <T : BaseEntity> isUnderModeration(): Specification<T> {
        return hasStatus(Status.UNDER_MODERATION)
    }

    fun <T : BaseEntity> isModerationDeclined(): Specification<T> {
        return hasStatus(Status.MODERATION_DECLINED)
    }

    fun <T : BaseEntity> isDraft(): Specification<T> {
        return hasStatus(Status.DRAFT)
    }

    fun <T : BaseEntity> hasAuthorId(
        id: UUID
    ): Specification<T> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(
                root.get<Any>("author")
                    .get<UUID>("id"),
                id
            )
        }
    }

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

    fun <T : BaseEntity, D : Comparable<D>> activeInPeriodSpec(
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

}