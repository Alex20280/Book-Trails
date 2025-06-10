package com.network_module.model.request

data class EmailRegistrationRequest(
    val email: String,
    val password: String,
    val name: String
)