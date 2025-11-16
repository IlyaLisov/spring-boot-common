package io.github.ilyalisov.common.web.dto

data class MessageDto(
    val message: String,
    val errors: Map<String, String>
) {
    constructor(message: String) : this(message, emptyMap())
}
