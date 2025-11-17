package io.github.ilyalisov.common.model

class ResourceNotFoundException(
    message: String? = null
) : RuntimeException(message)

class AccessDeniedException(
    message: String? = null
) : RuntimeException(message)

class ResourceAlreadyExistsException(
    message: String? = null
) : RuntimeException(message)

class DataNotValidException(
    message: String? = null
) : RuntimeException(message)

class TokenNotValidException(
    message: String? = null
) : RuntimeException(message)

class UploadException(
    message: String? = null
) : RuntimeException(message)

class NotEnoughMoneyException(
    message: String? = null
) : RuntimeException(message)