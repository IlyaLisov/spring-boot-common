package io.github.ilyalisov.common.web.dto

/**
 * Data transfer object for standardized API response messages with optional
 * error details. This class provides a consistent structure for both success
 * messages and validation error responses throughout the application.
 *
 * @param message the main message describing the result or error
 * @param errors a map of field-specific error messages, where keys represent
 *        field names and values contain error descriptions
 *
 * @author Ilya Lisov
 * @since 0.1.0
 */
data class MessageDto(
    val message: String,
    val errors: Map<String, String>
) {
    /**
     * Secondary constructor for creating a MessageDto with only a main message
     * and no field-specific errors. Useful for simple success messages or
     * general error notifications without detailed field validation.
     *
     * @param message the main message describing the result or error
     */
    constructor(message: String) : this(message, emptyMap())
}