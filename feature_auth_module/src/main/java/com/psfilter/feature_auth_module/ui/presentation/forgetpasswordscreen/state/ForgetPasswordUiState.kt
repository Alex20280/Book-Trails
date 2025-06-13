package com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.state

import com.network_module.errorhandling.DataError
import com.network_module.model.response.ResendVerificationCodeResponse

sealed interface ForgetPasswordUiState {
    data class Success(val response: ResendVerificationCodeResponse) : ForgetPasswordUiState
    data class Error(val message: String, val error: DataError.ResendEmailVerificationCodeAuth) : ForgetPasswordUiState
    data object Loading : ForgetPasswordUiState
    data object None : ForgetPasswordUiState
}