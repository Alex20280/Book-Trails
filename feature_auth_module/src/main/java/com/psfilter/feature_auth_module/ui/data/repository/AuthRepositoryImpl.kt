package com.psfilter.feature_auth_module.ui.data.repository

import com.network_module.api.AuthApi
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.request.ResendVerificationCodeRequest
import com.network_module.model.response.EmailRegistrationResponse
import com.network_module.model.response.EmailVerificationResponse
import com.network_module.model.response.ResendVerificationCodeResponse
import com.psfilter.feature_auth_module.ui.domain.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketTimeoutException

class AuthRepositoryImpl(
    private val registerApi: AuthApi,
    private val ioDispatcher: CoroutineDispatcher,
    //private val firebaseAuth: FirebaseAuth
) : AuthRepository {


    override suspend fun registerWithEmailAndPassword(
        registrationRequest: EmailRegistrationRequest
    ): RequestResult<EmailRegistrationResponse, DataError.EmailPasswordAuth> =
        withContext(ioDispatcher) {
            try {
                val response = registerApi.registerUserWithEmailAndPassword(registrationRequest)
                if (response.isSuccessful && response.body() != null) {
                    RequestResult.Success(response.body()!!)
                } else {
                    val errorType = when (response.code()) {
                        400 -> DataError.EmailPasswordAuth.INCORRECT_EMAIL_FORMAT
                        409 -> {
                            val errorBody = response.errorBody()?.string()
                            parse409Error(errorBody)
                        }
                        else -> DataError.EmailPasswordAuth.UNEXPECTED_ERROR
                    }
                    RequestResult.Error(errorType)
                }
            } catch (exception: SocketTimeoutException) {
                RequestResult.Error(DataError.EmailPasswordAuth.NETWORK_TIMEOUT)
            } catch (exception: IOException) {
                RequestResult.Error(DataError.EmailPasswordAuth.NETWORK_ERROR)
            } catch (exception: Exception) {
                exception.printStackTrace()
                RequestResult.Error(DataError.EmailPasswordAuth.UNEXPECTED_ERROR)
            }
        }


    override suspend fun verifyEmailWithVerificationCode(
        emailVerificationRequest: EmailVerificationRequest
    ): RequestResult<EmailVerificationResponse, DataError.EmailVerificationAuth> =
        withContext(ioDispatcher) {
            try {
                val response = registerApi.verifyEmail(emailVerificationRequest)
                if (response.isSuccessful && response.body() != null) {
                    RequestResult.Success(response.body()!!)
                } else {
                    val error = if (response.code() == 400) {
                        DataError.EmailVerificationAuth.INVALID_VERIFICATION_TOKEN
                    } else {
                        DataError.EmailVerificationAuth.UNEXPECTED_ERROR
                    }
                    RequestResult.Error(error)
                }
            } catch (exception: SocketTimeoutException) {
                RequestResult.Error(DataError.EmailVerificationAuth.NETWORK_TIMEOUT)
            } catch (exception: IOException) {
                RequestResult.Error(DataError.EmailVerificationAuth.NETWORK_ERROR)
            } catch (exception: Exception) {
                exception.printStackTrace()
                RequestResult.Error(DataError.EmailVerificationAuth.UNEXPECTED_ERROR)
            }
        }

    override suspend fun resendEmailVerificationCode(
        resendVerificationCodeRequest: ResendVerificationCodeRequest
    ): RequestResult<ResendVerificationCodeResponse, DataError.ResendEmailVerificationCodeAuth> =
        withContext(ioDispatcher) {
            try {
                val response = registerApi.resendEmailCode(resendVerificationCodeRequest)
                if (response.isSuccessful && response.body() != null) {
                    val isResent = response.body()!!
                    RequestResult.Success(ResendVerificationCodeResponse(isResent = isResent))
                } else {
                    val error = if (response.code() == 400) {
                        DataError.ResendEmailVerificationCodeAuth.NOT_FOUND
                    } else {
                        DataError.ResendEmailVerificationCodeAuth.UNEXPECTED_ERROR
                    }
                    RequestResult.Error(error)
                }
            } catch (exception: SocketTimeoutException) {
                RequestResult.Error(DataError.ResendEmailVerificationCodeAuth.NETWORK_TIMEOUT)
            } catch (exception: IOException) {
                RequestResult.Error(DataError.ResendEmailVerificationCodeAuth.NETWORK_ERROR)
            } catch (exception: Exception) {
                exception.printStackTrace()
                RequestResult.Error(DataError.ResendEmailVerificationCodeAuth.UNEXPECTED_ERROR)
            }
        }

    private fun parse409Error(errorBody: String?): DataError.EmailPasswordAuth {
        return when {
            errorBody?.contains("verify your email", ignoreCase = true) == true
                -> {
                DataError.EmailPasswordAuth.ACCOUNT_ALREADY_EXISTS_BUT_NOT_VERIFIED
            }

            errorBody?.contains("already exists", ignoreCase = true) == true
                -> {
                DataError.EmailPasswordAuth.ACCOUNT_ALREADY_IN_USE
            }

            else -> DataError.EmailPasswordAuth.UNEXPECTED_ERROR
        }
    }

    /*    override suspend fun resendEmailVerificationCode(
            resendVerificationCodeRequest: ResendVerificationCodeRequest
        ): RequestResult<ResendVerificationCodeResponse, DataError.ResendEmailVerificationCodeAuth> =
            withContext(ioDispatcher) {
                try {
                    val response = registerApi.resendEmailCode(resendVerificationCodeRequest)
                    if (response.isSuccessful && response.body() != null) {
                        RequestResult.Success(response.body()!!)
                    } else {
                        val error = if (response.code() == 400) {
                            DataError.ResendEmailVerificationCodeAuth.NOT_FOUND
                        } else {
                            DataError.ResendEmailVerificationCodeAuth.UNEXPECTED_ERROR
                        }
                        RequestResult.Error(error)
                    }
                } catch (exception: SocketTimeoutException) {
                    RequestResult.Error(DataError.ResendEmailVerificationCodeAuth.NETWORK_TIMEOUT)
                } catch (exception: IOException) {
                    RequestResult.Error(DataError.ResendEmailVerificationCodeAuth.NETWORK_ERROR)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    RequestResult.Error(DataError.ResendEmailVerificationCodeAuth.UNEXPECTED_ERROR)
                }
            }*/


    /*    override suspend fun receiveFirebaseId(): String? {
            return firebaseAuth.auth.uid
        }

        fun signInWithCustomToken(token: String): Task<AuthResult> {
            return firebaseAuth.signInWithCustomToken(token)
        }*/

}