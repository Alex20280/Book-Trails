package com.psfilter.feature_auth_module.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository

class EmailLogOutUseCase(
    private val emailAuthRepository: EmailAuthRepository
) {
    suspend fun logOut() {
        emailAuthRepository.logOut()
    }

    suspend fun getCurrentUserProvider(): List<String>?{
       return emailAuthRepository.getAuthProviders()
    }

}