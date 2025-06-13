package com.psfilter.feature_auth_module.ui.data.model

import com.network_module.model.request.LoginWithEmailPassRequest
import com.network_module.model.request.ResendVerificationCodeRequest

data class ForgetPasswordModel(
    val email: String,
    val isResent: Boolean,
)

fun ForgetPasswordModel.toResendVerificationCodeRequest(): ResendVerificationCodeRequest {
    return ResendVerificationCodeRequest(
        email = this.email,
        isResent = this.isResent
    )
}
