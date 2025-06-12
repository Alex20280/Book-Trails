package com.network_module.model.request

data class EmailVerificationRequest(
    val email: String,
    val code: String
)
