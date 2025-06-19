package com.psfilter.feature_auth_module.ui.presentation.createnewpassword.state

import com.network_module.errorhandling.DataError
import com.network_module.model.response.SetNewPasswordResponse

sealed interface SetNewPassPasswordUiState {
    data class Success(val response: SetNewPasswordResponse) : SetNewPassPasswordUiState
    data class Error(val message: String, val error: DataError.SetNewPasswordAuth) : SetNewPassPasswordUiState
    data object Loading : SetNewPassPasswordUiState
    data object None : SetNewPassPasswordUiState
}