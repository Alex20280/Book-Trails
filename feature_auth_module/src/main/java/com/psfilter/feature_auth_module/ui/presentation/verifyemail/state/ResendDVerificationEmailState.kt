package com.psfilter.feature_auth_module.ui.presentation.verifyemail.state

import com.network_module.model.response.EmailVerificationResponse
import com.network_module.model.response.ResendVerificationCodeResponse

sealed interface ResendDVerificationEmailState {
    data class Success(val response: ResendVerificationCodeResponse) : ResendDVerificationEmailState
    data class Error(val message: String) : ResendDVerificationEmailState
    object Loading : ResendDVerificationEmailState
    object None : ResendDVerificationEmailState
}