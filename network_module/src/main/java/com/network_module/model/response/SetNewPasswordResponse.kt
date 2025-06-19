package com.network_module.model.response

data class SetNewPasswordResponse(
    val existingUser: ExistingUser,
    val accessToken: String
)

data class ExistingUser(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val image: String,
    val subscriptionType: String,
    val isLoggedIn: Boolean,
    val isVerifyEmail: Boolean
)