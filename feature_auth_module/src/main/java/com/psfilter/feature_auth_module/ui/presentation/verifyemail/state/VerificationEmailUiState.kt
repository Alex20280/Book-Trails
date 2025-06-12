package com.psfilter.feature_auth_module.ui.presentation.verifyemail.state

import com.network_module.model.response.EmailVerificationResponse

sealed interface VerificationEmailUiState {
    data class Success(val response: EmailVerificationResponse) : VerificationEmailUiState
    data class Error(val message: String) : VerificationEmailUiState
    object Loading : VerificationEmailUiState
    object None : VerificationEmailUiState
}