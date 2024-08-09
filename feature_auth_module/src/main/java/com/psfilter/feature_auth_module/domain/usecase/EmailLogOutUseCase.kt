package com.psfilter.feature_auth_module.domain.usecase

import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository
import com.psfilter.feature_auth_module.domain.repository.GoogleAuthRepository

class EmailLogOutUseCase(
    private val emailAuthRepository: EmailAuthRepository,
    private val googleAuthRepository: GoogleAuthRepository
) {
    suspend fun logOut() {
        emailAuthRepository.logOut()
        googleAuthRepository.logOutWithGoogle()
    }
}