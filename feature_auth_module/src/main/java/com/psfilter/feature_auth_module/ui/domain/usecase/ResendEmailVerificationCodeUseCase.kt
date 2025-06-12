package com.psfilter.feature_auth_module.ui.domain.usecase

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.ResendVerificationCodeRequest
import com.network_module.model.response.ResendVerificationCodeResponse
import com.psfilter.feature_auth_module.ui.domain.AuthRepository


class ResendEmailVerificationCodeUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        resendVerificationCodeRequest: ResendVerificationCodeRequest
    ): RequestResult<ResendVerificationCodeResponse, DataError.ResendEmailVerificationCodeAuth> =
        authRepository.resendEmailVerificationCode(resendVerificationCodeRequest)
}