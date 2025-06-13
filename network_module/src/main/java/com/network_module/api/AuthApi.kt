package com.network_module.api

import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.request.LoginWithEmailPassRequest
import com.network_module.model.request.ResendVerificationCodeRequest
import com.network_module.model.response.EmailRegistrationResponse
import com.network_module.model.response.EmailVerificationResponse
import com.network_module.model.response.LoginWithEmailPassResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun registerUserWithEmailAndPassword(@Body emailRegistrationRequest: EmailRegistrationRequest): Response<EmailRegistrationResponse>

    @PATCH("auth/verify-email")
    suspend fun verifyEmail(@Body emailVerificationRequest: EmailVerificationRequest): Response<EmailVerificationResponse>

    @PATCH("auth/forget-password")
    suspend fun resendEmailCode(@Body resendVerificationCodeRequest: ResendVerificationCodeRequest): Response<Boolean>

    @POST("auth/login")
    suspend fun loginWithEmailAndPassword(@Body loginWithEmailPassRequest: LoginWithEmailPassRequest): Response<LoginWithEmailPassResponse>

}
