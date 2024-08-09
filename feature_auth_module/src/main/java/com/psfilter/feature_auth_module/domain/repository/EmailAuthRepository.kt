package com.psfilter.feature_auth_module.domain.repository

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

abstract class EmailAuthRepository {

    abstract suspend fun registerUser(email: String, password: String): RequestResult<Task<AuthResult>, DataError.Firebase>

    abstract suspend fun loginUser(email: String, password: String): RequestResult<AuthResult, DataError.Firebase>

    abstract suspend fun resetPassword(email: String): RequestResult<Task<Void>, DataError.Firebase>

    abstract suspend fun getUserUd() : RequestResult<String?, DataError>

    abstract suspend fun logOut(): Unit
}