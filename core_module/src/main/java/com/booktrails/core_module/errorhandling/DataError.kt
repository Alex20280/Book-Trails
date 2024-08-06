package com.booktrails.core_module.errorhandling

sealed interface DataError : Error {

    enum class Firebase : DataError {
        REGISTRATION_PROBLEM,
        LOGIN_PROBLEM,
        RESET_PROBLEM,
        UID_PROBLEM
    }

    /*    enum class Login: DataError {
            LOGIN_IS_NOT_CORRECT,
            EMAILS_NOT_MATCH
        }
        enum class Password: DataError {
            PASSWORD_IS_TOO_SHORT,
            PASSWORD_NOT_HAVE_UPPER_CASE_LETTER,
            PASSWORD_NOT_HAVE_DIGITS,
            PASSWORD_NOT_SPECIAL_CHARACTERS
        }*/
    enum class AuthenticationError : DataError {
        LOGIN_IS_NOT_CORRECT,
        PASSWORDS_NOT_MATCH,
        PASSWORD_IS_TOO_SHORT,
        PASSWORD_NOT_HAVE_UPPER_CASE_LETTER,
        PASSWORD_NOT_HAVE_DIGITS,
        PASSWORD_NOT_SPECIAL_CHARACTERS
    }

}