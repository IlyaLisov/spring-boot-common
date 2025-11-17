package io.github.ilyalisov.common.model

/**
 * Enumeration representing the lifecycle states of entities in the system.
 * Each status defines a specific state in the entity's lifecycle and is used
 * for filtering, business logic, and access control throughout the
 * application.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
enum class Status {

    /**
     * Entity is fully active and operational. This is the default state for
     * most entities and indicates they are visible and accessible according
     * to normal business rules.
     */
    ACTIVE,

    /**
     * Entity is registered but not yet activated. Typically used for users
     * who have registered but not yet completed email verification or other
     * activation steps. Limited functionality available.
     */
    NOT_ACTIVE,

    /**
     * Entity has been temporarily blocked from normal operations. This can
     * be due to policy violations, security concerns, or administrative
     * action. Entity data is preserved but access is restricted.
     */
    BLOCKED,

    /**
     * Entity has been soft-deleted and is no longer active in the system.
     * The data is retained for historical purposes but is excluded from
     * normal queries and operations. Can potentially be restored.
     */
    DELETED,

    /**
     * Entity is undergoing review by moderators or administrators before
     * being made publicly available. Common for user-generated content
     * that requires approval.
     */
    UNDER_MODERATION,

    /**
     * Entity has been reviewed by moderators and rejected for publication.
     * The entity failed to meet content guidelines or quality standards
     * during the moderation process.
     */
    MODERATION_DECLINED,

    /**
     * Entity is in progress and not yet ready for publication or active use.
     * Typically used for content that is being drafted and saved for later
     * completion or submission.
     */
    DRAFT

}