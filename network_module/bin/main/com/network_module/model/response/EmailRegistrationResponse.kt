package com.network_module.model.response

data class EmailRegistrationResponse (
    val email: String,
    val name: String,
    val id: Int,
    val image: String,
    val role: String,
    val subscriptionType: String,
    val isLoggedIn: Boolean,
    val isVerifyEmail: Boolean
)