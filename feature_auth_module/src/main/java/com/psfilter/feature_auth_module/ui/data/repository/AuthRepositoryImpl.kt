package com.psfilter.feature_auth_module.ui.data.repository

import android.util.Log
import com.network_module.api.AuthApi
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.response.EmailRegistrationResponse
import com.network_module.model.response.EmailVerificationResponse
import com.psfilter.feature_auth_module.ui.domain.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class AuthRepositoryImpl(
    private val registerApi: AuthApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {


    override suspend fun registerWithEmailAndPassword(
        registrationRequest: EmailRegistrationRequest
    ): RequestResult<EmailRegistrationResponse, DataError.EmailPasswordAuth> =
        withContext(Dispatchers.IO) {
            try {
                val response = registerApi.registerUserWithEmailAndPassword(registrationRequest)
                if (response.isSuccessful && response.body() != null) {
                    RequestResult.Success(response.body()!!)
                } else {
                    Log.d("MyErrorCode", response.code().toString() )
                    val errorType = when (response.code()) {
                        400 -> DataError.EmailPasswordAuth.INCORRECT_EMAIL_FORMAT
                        409 -> DataError.EmailPasswordAuth.ACCOUNT_ALREADY_EXISTS
                        else -> DataError.EmailPasswordAuth.UNEXPECTED_ERROR
                    }
                    RequestResult.Error(errorType)
                }
            } catch (exception: SocketTimeoutException) {
                RequestResult.Error(DataError.EmailPasswordAuth.INCORRECT_EMAIL_FORMAT)
            } catch (exception: Exception) {
                exception.printStackTrace()
                RequestResult.Error(DataError.EmailPasswordAuth.INCORRECT_EMAIL_FORMAT)
            }
        }



    override suspend fun verifyEmailWithVerificationCode(emailVerificationRequest: EmailVerificationRequest): EmailVerificationResponse {
        TODO("Not yet implemented")
    }
}