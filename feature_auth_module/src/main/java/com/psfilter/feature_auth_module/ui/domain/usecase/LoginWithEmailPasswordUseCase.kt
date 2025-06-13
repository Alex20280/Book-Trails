package com.psfilter.feature_auth_module.ui.domain.usecase

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.LoginWithEmailPassRequest
import com.network_module.model.response.LoginWithEmailPassResponse
import com.psfilter.feature_auth_module.ui.domain.AuthRepository

class LoginWithEmailPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        loginWithEmailPassRequest: LoginWithEmailPassRequest
    ): RequestResult<LoginWithEmailPassResponse, DataError.EmailPasswordAuth> =
        authRepository.loginWithEmailAndPassword(loginWithEmailPassRequest)
}