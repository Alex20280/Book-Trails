package com.network_module.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("notification")
    suspend fun testNotification(@Body pushNotificationRequest: PushNotificationRequest)
}

data class PushNotificationRequest(
    val title: String,
    val body: String,
    val deviceId: String
)