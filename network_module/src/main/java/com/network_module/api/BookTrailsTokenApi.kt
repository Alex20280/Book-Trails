package com.network_module.api

import com.network_module.model.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface BookTrailsTokenApi {

    @GET("/api/v1/auth/refresh-token")
    suspend fun refreshToken(
        @Header("Authorization") authorization: String
    ): Response<RefreshTokenResponse>
}