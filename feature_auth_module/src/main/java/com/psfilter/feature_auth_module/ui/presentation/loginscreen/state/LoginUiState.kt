package com.psfilter.feature_auth_module.ui.presentation.loginscreen.state

import com.network_module.errorhandling.DataError
import com.network_module.model.response.EmailRegistrationResponse
import com.network_module.model.response.LoginWithEmailPassResponse


sealed interface LoginUiState {
    data class Success(val response: LoginWithEmailPassResponse) : LoginUiState
    data class Error(val message: String, val error: DataError.EmailPasswordAuth) : LoginUiState
    object Loading : LoginUiState
    object None : LoginUiState
}