package com.psfilter.feature_auth_module.ui.domain.usecase

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.response.EmailVerificationResponse
import com.psfilter.feature_auth_module.ui.domain.AuthRepository


class VerifyEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        emailVerificationRequest: EmailVerificationRequest
    ): RequestResult<EmailVerificationResponse, DataError.EmailVerificationAuth> =
        authRepository.verifyEmailWithVerificationCode(emailVerificationRequest)
}