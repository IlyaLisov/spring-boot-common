package io.github.ilyalisov.common.service

import java.util.*

/**
 * Interface defining operations for entities that require moderation
 * workflows. Extends [Blockable] to provide comprehensive content
 * management capabilities including approval, rejection, and blocking
 * functionality.
 *
 * Entities implementing this interface typically represent user-generated
 * content that requires review before publication, such as posts, comments,
 * reviews, or other community content.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @see Blockable
 */
interface Moderatable : Blockable {

    /**
     * Approves the entity with the specified ID, allowing it to be publicly
     * visible and accessible. This method typically updates the entity's
     * status to [io.github.ilyalisov.common.model.Status.ACTIVE] and may
     * trigger additional business logic such as notifications or indexing.
     *
     * @param id the UUID of the entity to be approved
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException if
     * no entity with the given ID exists
     */
    fun allow(
        id: UUID
    )

    /**
     * Rejects the entity with the specified ID, preventing it from being
     * publicly visible. This method typically updates the entity's status
     * to [io.github.ilyalisov.common.model.Status.MODERATION_DECLINED] and
     * may include reasons for rejection or additional moderation notes.
     *
     * @param id the UUID of the entity to be rejected
     * @throws io.github.ilyalisov.common.model.ResourceNotFoundException if
     * no entity with the given ID exists
     */
    fun disallow(
        id: UUID
    )

}