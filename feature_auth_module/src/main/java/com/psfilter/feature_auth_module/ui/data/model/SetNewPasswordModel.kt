package com.psfilter.feature_auth_module.ui.data.model

import com.network_module.model.request.SetNewPasswordRequest

data class SetNewPasswordModel(
    val email: String,
    val code: String,
    val newPassword: String
)

fun SetNewPasswordModel.toSetNewPasswordRequest(): SetNewPasswordRequest {
    return SetNewPasswordRequest(
        email = this.email,
        code = this.code,
        newPassword = this.newPassword
    )
}
