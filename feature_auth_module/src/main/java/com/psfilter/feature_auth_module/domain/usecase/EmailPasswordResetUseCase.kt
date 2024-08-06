package com.psfilter.feature_auth_module.domain.usecase

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.psfilter.feature_auth_module.domain.EmailAuthRepository

class EmailPasswordResetUseCase(
    private val emailAuthRepository: EmailAuthRepository
)  {

    suspend fun resetPassword(email: String): RequestResult<Task<Void>, DataError.Firebase> {
        return emailAuthRepository.resetPassword(email)
    }
}