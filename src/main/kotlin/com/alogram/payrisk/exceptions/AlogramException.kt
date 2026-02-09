package com.alogram.payrisk.exceptions

open class AlogramException(
    message: String,
    val statusCode: Int = 0,
    val responseBody: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    override fun toString(): String {
        return "${this::class.simpleName} (Status: $statusCode): ${message ?: ""}"
    }
}

class AuthenticationException(message: String, statusCode: Int, responseBody: String?) :
    AlogramException(message, statusCode, responseBody)

class RateLimitException(message: String, statusCode: Int, responseBody: String?) :
    AlogramException(message, statusCode, responseBody)

class ValidationException(message: String, statusCode: Int, responseBody: String?) :
    AlogramException(message, statusCode, responseBody)

class InternalServerException(message: String, statusCode: Int, responseBody: String?) :
    AlogramException(message, statusCode, responseBody)

class ScopedAccessError(message: String) :
    AlogramException(message, 403, null)
