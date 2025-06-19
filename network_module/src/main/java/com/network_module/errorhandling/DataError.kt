package com.network_module.errorhandling

sealed interface Error
sealed interface DataError : Error {

    enum class EmailPasswordRegistration : DataError {
        INCORRECT_EMAIL_FORMAT,
        ACCOUNT_ALREADY_EXISTS_BUT_NOT_VERIFIED,
        ACCOUNT_ALREADY_IN_USE,
        NETWORK_TIMEOUT,
        NETWORK_ERROR,
        UNEXPECTED_ERROR
    }

    enum class EmailVerificationAuth : DataError {
        INVALID_VERIFICATION_TOKEN,
        NETWORK_TIMEOUT,
        NETWORK_ERROR,
        UNEXPECTED_ERROR
    }

    enum class ResendEmailVerificationCodeAuth : DataError {
        NOT_FOUND,
        NETWORK_TIMEOUT,
        NETWORK_ERROR,
        UNEXPECTED_ERROR
    }

    enum class EmailPasswordAuth : DataError {
        UNAUTHORIZED,
        EMAIL_NOT_VERIFIED,
        NETWORK_TIMEOUT,
        NETWORK_ERROR,
        UNEXPECTED_ERROR,
        NOT_FOUND
    }

    enum class SetNewPasswordAuth : DataError {
        USER_NOT_FOUND,
        INCORRECT_PASSWORD,
        INVALID_RESET_CODE,
        NETWORK_TIMEOUT,
        NETWORK_ERROR,
        UNEXPECTED_ERROR,
    }


    enum class ForgetPasswordAuth : DataError {
        NOT_FOUND,
        NETWORK_TIMEOUT,
        NETWORK_ERROR,
        UNEXPECTED_ERROR
    }

}