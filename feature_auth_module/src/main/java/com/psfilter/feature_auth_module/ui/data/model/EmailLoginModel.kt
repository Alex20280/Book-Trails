package com.psfilter.feature_auth_module.ui.data.model

import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.request.LoginWithEmailPassRequest

data class EmailLoginModel(
    val email: String,
    val password: String,
)

fun EmailLoginModel.toLoginWithEmailPassRequest(): LoginWithEmailPassRequest {
    return LoginWithEmailPassRequest(
        email = this.email,
        password = this.password
    )
}