package com.psfilter.feature_auth_module.ui.data.repository

import com.network_module.api.AuthApi
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailRegistrationRequest
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.request.LoginWithEmailPassRequest
import com.network_module.model.request.ResendVerificationCodeRequest
import com.network_module.model.request.SetNewPasswordRequest
import com.network_module.model.response.EmailRegistrationResponse
import com.network_module.model.response.EmailVerificationResponse
import com.network_module.model.response.LoginWithEmailPassResponse
import com.network_module.model.response.ResendVerificationCodeResponse
import com.network_module.model.response.SetNewPasswordResponse
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
    ): RequestResult<EmailRegistrationResponse, DataError.EmailPasswordRegistration> =
        withContext(ioDispatcher) {
            try {
                val response = registerApi.registerUserWithEmailAndPassword(registrationRequest)
                if (response.isSuccessful && response.body() != null) {
                    RequestResult.Success(response.body()!!)
                } else {
                    val errorType = when (response.code()) {
                        400 -> DataError.EmailPasswordRegistration.INCORRECT_EMAIL_FORMAT
                        403 -> DataError.EmailPasswordRegistration.ACCOUNT_ALREADY_EXISTS_BUT_NOT_VERIFIED
                        409 -> DataError.EmailPasswordRegistration.ACCOUNT_ALREADY_IN_USE

                        else -> DataError.EmailPasswordRegistration.UNEXPECTED_ERROR
                    }
                    RequestResult.Error(errorType)
                }
            } catch (exception: SocketTimeoutException) {
                RequestResult.Error(DataError.EmailPasswordRegistration.NETWORK_TIMEOUT)
            } catch (exception: IOException) {
                RequestResult.Error(DataError.EmailPasswordRegistration.NETWORK_ERROR)
            } catch (exception: Exception) {
                exception.printStackTrace()
                RequestResult.Error(DataError.EmailPasswordRegistration.UNEXPECTED_ERROR)
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

    override suspend fun loginWithEmailAndPassword(
        loginWithEmailPassRequest: LoginWithEmailPassRequest
    ): RequestResult<LoginWithEmailPassResponse, DataError.EmailPasswordAuth> =
        withContext(ioDispatcher) {
            try {
                val response = registerApi.loginWithEmailAndPassword(loginWithEmailPassRequest)
                if (response.isSuccessful && response.body() != null) {
                    RequestResult.Success(response.body()!!)
                } else {
                    val errorType = when (response.code()) {
                        401 -> DataError.EmailPasswordAuth.UNAUTHORIZED
                        403 -> DataError.EmailPasswordAuth.EMAIL_NOT_VERIFIED
                        404 -> DataError.EmailPasswordAuth.NOT_FOUND

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

    override suspend fun setNewPassword(
        setNewPasswordRequest: SetNewPasswordRequest
    ): RequestResult<SetNewPasswordResponse, DataError.SetNewPasswordAuth> =
        withContext(ioDispatcher) {
            try {
                val response = registerApi.setNewPassword(setNewPasswordRequest)
                if (response.isSuccessful && response.body() != null) {
                    RequestResult.Success(response.body()!!)
                } else {
                    val errorType = when (response.code()) {
                        400 -> {
                            val errorMessage = response.errorBody()?.string() ?: ""
                            handle400Error(errorMessage)
                        }

                        404 -> DataError.SetNewPasswordAuth.USER_NOT_FOUND
                        else -> DataError.SetNewPasswordAuth.UNEXPECTED_ERROR
                    }
                    RequestResult.Error(errorType)
                }
            } catch (exception: SocketTimeoutException) {
                RequestResult.Error(DataError.SetNewPasswordAuth.NETWORK_TIMEOUT)
            } catch (exception: IOException) {
                RequestResult.Error(DataError.SetNewPasswordAuth.NETWORK_ERROR)
            } catch (exception: Exception) {
                exception.printStackTrace()
                RequestResult.Error(DataError.SetNewPasswordAuth.UNEXPECTED_ERROR)
            }
        }

    private fun handle400Error(errorMessage: String): DataError.SetNewPasswordAuth {
        return when {
            errorMessage.contains(
                "password must contain one capital letter, one digit and one special character",
                ignoreCase = true
            ) -> {
                DataError.SetNewPasswordAuth.INCORRECT_PASSWORD
            }

            errorMessage.contains("Invalid reset code", ignoreCase = true) -> {
                DataError.SetNewPasswordAuth.INVALID_RESET_CODE
            }

            else -> DataError.SetNewPasswordAuth.UNEXPECTED_ERROR
        }
    }

    /*    override suspend fun receiveFirebaseId(): String? {
            return firebaseAuth.auth.uid
        }

        fun signInWithCustomToken(token: String): Task<AuthResult> {
            return firebaseAuth.signInWithCustomToken(token)
        }*/

}