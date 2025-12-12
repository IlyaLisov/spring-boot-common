package io.github.ilyalisov.common.web.dto

/**
 * Validation group interface for scenarios where entity validation is
 * required specifically during publishing operations. Use this group to
 * define constraints that should only be checked when an entity is being
 * published or made publicly available.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @example
 * ```
 * @NotBlank(groups = [OnPublish::class])
 * val content: String
 * ```
 */
interface OnPublish

/**
 * Validation group interface for scenarios where entity validation is
 * required specifically during update operations. Use this group to define
 * constraints that should only be checked when updating existing entities,
 * allowing different validation rules than during creation.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @example
 * ```
 * @NotNull(groups = [OnUpdate::class])
 * val version: Long
 * ```
 */
interface OnUpdate

/**
 * Validation group interface for scenarios where entity validation is
 * required specifically during creation operations. Use this group to
 * define constraints that should only be checked when creating new entities,
 * allowing different validation rules than during updates.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @example
 * ```
 * @NotBlank(groups = [OnCreate::class])
 * val initialPassword: String
 * ```
 */
interface OnCreate

/**
 * Validation group interface for scenarios involving nested object
 * identification. Use this group to validate that nested objects or
 * references to contain valid identifiers when required by the business logic.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @example
 * ```
 * @Valid(groups = [NestedObjectId::class])
 * val author: UserReference
 * ```
 */
interface NestedObjectId

/**
 * Validation group interface for flexible ID validation scenarios. Use this
 * group when an ID field should be validated regardless of whether it's
 * provided (for updates) or not provided (for creations), allowing for
 * conditional ID presence validation.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @example
 * ```
 * @Null(groups = [OnCreate::class])
 * @NotNull(groups = [OnUpdate::class])
 * val id: Long?
 * ```
 */
interface OnAnyId

const val MAX_FIELD_LENGTH = 255;