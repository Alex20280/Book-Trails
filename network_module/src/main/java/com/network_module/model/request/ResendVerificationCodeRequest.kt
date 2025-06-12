package com.network_module.model.request

data class ResendVerificationCodeRequest(
    val email: String,
    val isResent: Boolean
)