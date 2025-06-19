package com.network_module.model.request

data class SetNewPasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String
)
