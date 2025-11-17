package io.github.ilyalisov.common.service

import java.util.*

/**
 * Interface defining operations for entities that can be blocked and
 * unblocked. Implementing this interface provides a standardized way to
 * manage the blocking state of entities such as users, posts, comments,
 * or other domain objects that require temporary suspension of access or
 * functionality.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
interface Blockable {

    /**
     * Blocks the entity with the specified ID, effectively suspending its
     * normal operations and access. The implementation should typically
     * update the entity's status to
     * [io.github.ilyalisov.common.model.Status.BLOCKED]
     * and apply any additional business logic required for blocking.
     *
     * @param id the UUID of the entity to be blocked
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException if
     * no entity with the given ID exists
     */
    fun block(
        id: UUID
    )

    /**
     * Unblocks the entity with the specified ID, restoring its normal
     * operations and access. The implementation should typically update
     * the entity's status to [io.github.ilyalisov.common.model.Status.ACTIVE]
     * and remove any blocking-related restrictions.
     *
     * @param id the UUID of the entity to be unblocked
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException if
     * no entity with the given ID exists
     */
    fun unblock(
        id: UUID
    )

}