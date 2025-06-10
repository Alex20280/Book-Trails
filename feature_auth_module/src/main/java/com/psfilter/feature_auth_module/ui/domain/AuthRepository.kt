package com.psfilter.feature_auth_module.ui.domain

import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.response.EmailRegistrationResponse
import com.network_module.model.response.EmailVerificationResponse

interface AuthRepository {

    suspend fun registerWithEmailAndPassword(registrationRequest: EmailRegistrationRequest): RequestResult<EmailRegistrationResponse, DataError.EmailPasswordAuth>

    suspend fun verifyEmailWithVerificationCode(emailVerificationRequest: EmailVerificationRequest): EmailVerificationResponse

}