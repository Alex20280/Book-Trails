package com.psfilter.feature_auth_module.ui.domain

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.request.LoginWithEmailPassRequest
import com.network_module.model.request.ResendVerificationCodeRequest
import com.network_module.model.request.SetNewPasswordRequest
import com.network_module.model.response.EmailRegistrationResponse
import com.network_module.model.response.EmailVerificationResponse
import com.network_module.model.response.LoginWithEmailPassResponse
import com.network_module.model.response.ResendVerificationCodeResponse
import com.network_module.model.response.SetNewPasswordResponse

interface AuthRepository {

    suspend fun registerWithEmailAndPassword(registrationRequest: EmailRegistrationRequest): RequestResult<EmailRegistrationResponse, DataError.EmailPasswordRegistration>

    suspend fun verifyEmailWithVerificationCode(emailVerificationRequest: EmailVerificationRequest): RequestResult<EmailVerificationResponse, DataError.EmailVerificationAuth>

    suspend fun resendEmailVerificationCode(resendVerificationCodeRequest: ResendVerificationCodeRequest): RequestResult<ResendVerificationCodeResponse, DataError.ResendEmailVerificationCodeAuth>

    suspend fun loginWithEmailAndPassword(loginWithEmailPassRequest: LoginWithEmailPassRequest): RequestResult<LoginWithEmailPassResponse, DataError.EmailPasswordAuth>

    suspend fun setNewPassword(setNewPasswordRequest: SetNewPasswordRequest): RequestResult<SetNewPasswordResponse, DataError.SetNewPasswordAuth>

}