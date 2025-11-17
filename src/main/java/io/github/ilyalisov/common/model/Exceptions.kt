package io.github.ilyalisov.common.model

/**
 * Exception thrown when a requested resource cannot be found in the system.
 * This typically occurs when attempting to access, update, or delete a
 * resource that does not exist, does not have ACTIVE status or has been
 * removed.
 *
 * @param message the detailed exception message, optional
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
class ResourceNotFoundException(
    message: String? = null
) : RuntimeException(message)

/**
 * Exception thrown when a user attempts to access a resource or perform an
 * action without sufficient permissions or authentication.
 * This exception corresponds to HTTP 403 Forbidden status.
 *
 * @param message the detailed exception message, optional
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
class AccessDeniedException(
    message: String? = null
) : RuntimeException(message)

/**
 * Exception thrown when attempting to create a resource that already exists
 * in the system with the same unique constraints (e.g., duplicate email,
 * username, or other unique fields).
 *
 * @param message the detailed exception message, optional
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
class ResourceAlreadyExistsException(
    message: String? = null
) : RuntimeException(message)

/**
 * Exception thrown when input data fails validation rules or business logic
 * constraints. This can include format violations, range errors, or other
 * data integrity issues.
 *
 * @param message the detailed exception message, optional
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
class DataNotValidException(
    message: String? = null
) : RuntimeException(message)

/**
 * Exception thrown when a provided token is invalid, expired, malformed, or
 * cannot be processed. This includes JWT tokens, activation tokens, and
 * other authentication tokens.
 *
 * @param message the detailed exception message, optional
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
class TokenNotValidException(
    message: String? = null
) : RuntimeException(message)

/**
 * Exception thrown when file upload operations fail due to various reasons
 * such as storage errors, network issues, file size limits, or unsupported
 * file formats.
 *
 * @param message the detailed exception message, optional
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
class UploadException(
    message: String? = null
) : RuntimeException(message)

/**
 * Exception thrown when a financial transaction or operation cannot be
 * completed due to insufficient funds or balance in the user's account.
 *
 * @param message the detailed exception message, optional
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
class NotEnoughMoneyException(
    message: String? = null
) : RuntimeException(message)