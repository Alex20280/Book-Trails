package com.network_module.model.response

data class LoginWithEmailPassResponse(
    val existingUser: User,
    val accessToken: String
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val image: String,
    val subscriptionType: String,
    val isLoggedIn: Boolean,
    val isVerifyEmail: Boolean
)