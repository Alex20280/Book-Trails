package com.psfilter.feature_auth_module.ui.data.model

import com.network_module.model.request.EmailRegistrationRequest

data class EmailRegistrationModel(
    val email: String,
    val password: String,
    val name: String,
    val confirmPassword: String
)

fun EmailRegistrationModel.toEmailRegistrationRequest(): EmailRegistrationRequest {
    return EmailRegistrationRequest(
        email = this.email,
        password = this.password,
        name = this.name
    )
}
