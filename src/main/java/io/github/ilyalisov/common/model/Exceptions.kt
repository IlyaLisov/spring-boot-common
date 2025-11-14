package io.github.ilyalisov.common.model

class ResourceNotFoundException : RuntimeException()

class AccessDeniedException : RuntimeException()

class ResourceAlreadyExistsException(
    message: String?
) : RuntimeException(message)

class DataNotValidException(
    message: String?
) : RuntimeException(message)

class TokenNotValidException(
    message: String?
) : RuntimeException(message)

class UploadException(
    message: String?
) : RuntimeException(message)

class NotEnoughMoneyException : RuntimeException()