package com.psfilter.feature_auth_module.ui.domain.usecase

import com.psfilter.feature_auth_module.ui.domain.AuthRepository

class GetNotificationUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun getNotification(){
        authRepository.testNotification()
    }
}