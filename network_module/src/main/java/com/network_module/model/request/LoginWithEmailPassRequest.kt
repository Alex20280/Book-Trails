package com.network_module.model.request

data class LoginWithEmailPassRequest (
    val email: String,
    val password: String
)