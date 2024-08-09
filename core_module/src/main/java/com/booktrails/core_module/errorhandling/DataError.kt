package com.booktrails.core_module.errorhandling

sealed interface DataError : Error {

    enum class Firebase : DataError {
        FIREBASE_AUTH_EXCEPTION,
        REGISTRATION_PROBLEM,
        LOGIN_PROBLEM,
        RESET_PROBLEM,
        UID_PROBLEM,
        UNEXPECTED_ERROR
    }

    enum class Gmail : DataError {
        AUTH_PROBLEM
    }
}