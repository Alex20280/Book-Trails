package com.psfilter.feature_auth_module.domain.usecase

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.psfilter.feature_auth_module.domain.EmailAuthRepository

class EmailSignInUseCase(
    private val emailAuthRepository: EmailAuthRepository
) {

    suspend fun registerUser(email: String, password: String): RequestResult<Task<AuthResult>, DataError.Firebase> {
        return emailAuthRepository.registerUser(email, password)
    }

}