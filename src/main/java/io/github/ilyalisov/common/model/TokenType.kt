package io.github.ilyalisov.common.model

/**
 * Enumeration representing different types of tokens used throughout the
 * application for various authentication, authorization, and business
 * process purposes. Each token type has specific characteristics including
 * expiration time, scope, and intended use case.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
enum class TokenType {

    /**
     * Short-lived token used for accessing protected resources and API
     * endpoints. Typically, has a short expiration time (minutes to hours)
     * and contains user authorization claims. Automatically renewed using
     * refresh tokens when expired.
     */
    ACCESS,

    /**
     * Long-lived token used to obtain new access tokens without requiring
     * user re-authentication. Stored securely and has a longer expiration
     * time (days to weeks). Can be revoked for security purposes.
     */
    REFRESH,

    /**
     * Token used for account activation and email verification processes.
     * Sent to users via email during registration to verify their email
     * address and activate their account. Typically, single-use.
     */
    ACTIVATION,

    /**
     * Token used for password reset and recovery procedures. Sent to users
     * when they request to reset their forgotten password. Provides
     * temporary access to password change functionality.
     */
    PASSWORD_RESET,

    /**
     * Generic token type for custom application-specific use cases not
     * covered by the standard token types. Can be used for various business
     * processes like email change confirmation, two-factor authentication,
     * or temporary access grants.
     */
    CUSTOM

}