package com.psfilter.feature_auth_module.ui.domain.usecase

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.response.EmailRegistrationResponse
import com.psfilter.feature_auth_module.ui.domain.AuthRepository

class RegisterWithEmailAndPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        registrationRequest: EmailRegistrationRequest
    ): RequestResult<EmailRegistrationResponse, DataError.EmailPasswordRegistration> =
        authRepository.registerWithEmailAndPassword(registrationRequest)
}