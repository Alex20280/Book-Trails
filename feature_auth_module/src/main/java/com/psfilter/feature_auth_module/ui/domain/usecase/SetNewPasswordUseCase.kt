package com.psfilter.feature_auth_module.ui.domain.usecase

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.SetNewPasswordRequest
import com.network_module.model.response.SetNewPasswordResponse
import com.psfilter.feature_auth_module.ui.domain.AuthRepository

class SetNewPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        setNewPasswordRequest: SetNewPasswordRequest
    ): RequestResult<SetNewPasswordResponse, DataError.SetNewPasswordAuth> =
        authRepository.setNewPassword(setNewPasswordRequest)
}



