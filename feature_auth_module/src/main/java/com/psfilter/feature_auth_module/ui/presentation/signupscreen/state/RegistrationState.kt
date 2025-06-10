package com.psfilter.feature_auth_module.ui.presentation.signupscreen.state

import com.network_module.model.response.EmailRegistrationResponse

sealed interface RegistrationUiState {
    data class Success(val response: EmailRegistrationResponse) : RegistrationUiState
    data class Error(val message: String) : RegistrationUiState
    object Loading : RegistrationUiState
    object None : RegistrationUiState
}