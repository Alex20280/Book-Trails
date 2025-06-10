package com.network_module.api

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.response.EmailRegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun registerUserWithEmailAndPassword(@Body emailRegistrationRequest: EmailRegistrationRequest): Response<EmailRegistrationResponse>

}
