package com.network_module.errorhandling

sealed interface Error
sealed interface DataError : Error {

    enum class EmailPasswordAuth : DataError {
        INCORRECT_EMAIL_FORMAT,
        ACCOUNT_ALREADY_EXISTS,
        UNEXPECTED_ERROR
    }

    enum class NetworkErrorType : DataError {
        BAD_REQUEST,
        TIMEOUT,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        INTERNAL_SERVER_ERROR,
        UNKNOWN_HTTP_ERROR
    }

    data class UnexpectedError(val exception: Throwable) : DataError

}