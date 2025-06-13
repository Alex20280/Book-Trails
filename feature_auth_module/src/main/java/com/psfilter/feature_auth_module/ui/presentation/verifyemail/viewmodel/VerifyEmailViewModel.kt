package com.psfilter.feature_auth_module.ui.presentation.verifyemail.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.request.EmailVerificationRequest
import com.network_module.model.request.ResendVerificationCodeRequest
import com.network_module.model.response.EmailVerificationResponse
import com.network_module.model.response.ResendVerificationCodeResponse
import com.psfilter.feature_auth_module.ui.domain.usecase.ResendEmailVerificationCodeUseCase
import com.psfilter.feature_auth_module.ui.domain.usecase.VerifyEmailUseCase
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateEmailForm
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.ConfirmEmailState
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.ResendDVerificationEmailState
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.VerificationEmailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerifyEmailViewModel(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val resendEmailVerificationCodeUseCase: ResendEmailVerificationCodeUseCase,
) : ViewModel() {

    private val _emailVerificationUiState =
        MutableStateFlow<VerificationEmailUiState>(VerificationEmailUiState.None)
    val emailVerificationUiState: StateFlow<VerificationEmailUiState> =
        _emailVerificationUiState.asStateFlow()

    private val _resendEmailVerificationUiState =
        MutableStateFlow<ResendDVerificationEmailState>(ResendDVerificationEmailState.None)
    val resendEmailVerificationUiState: StateFlow<ResendDVerificationEmailState> =
        _resendEmailVerificationUiState.asStateFlow()

    private val _formState = mutableStateOf(ConfirmEmailState())
    val formState: State<ConfirmEmailState> = _formState


    fun validateEmailOnFocusLost() {
        val email = _formState.value.email
        if (email.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                emailError = validateEmailForm(email)
            )
        }
    }

    fun updateEmail(newEmail: String) {
        _formState.value = _formState.value.copy(
            email = newEmail,
            emailError = if (newEmail.isNotEmpty()) null else _formState.value.emailError
        )
    }

    fun updateCode(newName: String) {
        _formState.value = _formState.value.copy(
            code = newName
        )
    }
    private fun hasValidationErrors(): Boolean {
        val state = _formState.value
        return state.emailError != null
    }


    fun verifyEmail() {
        val currentState = _formState.value

        val validatedState = currentState.copy(
            emailError = validateEmailForm(currentState.email),
        )

        _formState.value = validatedState

        if (hasValidationErrors()) {
            return
        }

        val emailVerificationModel = EmailVerificationRequest(
            email = validatedState.email,
            code = validatedState.code
        )

        viewModelScope.launch {
            _emailVerificationUiState.value = VerificationEmailUiState.Loading
            val response = verifyEmailUseCase.invoke(
                emailVerificationModel
            )
            handleEmailVerificationRequest(response)
        }
    }

    private fun handleEmailVerificationRequest(response: RequestResult<EmailVerificationResponse, DataError.EmailVerificationAuth>) {
        when (response) {
            is RequestResult.Success -> {
                _emailVerificationUiState.value = VerificationEmailUiState.Success(response.data)
            }

            is RequestResult.Error -> {
                val errorMessage = when (response.error) {
                    DataError.EmailVerificationAuth.INVALID_VERIFICATION_TOKEN ->
                        "Invalid code. Please request a new code"

                    DataError.EmailVerificationAuth.UNEXPECTED_ERROR ->
                        "Something went wrong. Please try again"

                    DataError.EmailVerificationAuth.NETWORK_TIMEOUT ->
                        "Couldn't connect. Please try again later"

                    DataError.EmailVerificationAuth.NETWORK_ERROR ->
                        "Check internet connection and try again"
                }
                _emailVerificationUiState.value = VerificationEmailUiState.Error(errorMessage)
            }

            else -> Unit
        }
    }

    fun resendCode() {
        val currentState = _formState.value

        val validatedState = currentState.copy(
            emailError = validateEmailForm(currentState.email),
        )

        _formState.value = validatedState

        if (hasValidationErrors()) {
            return
        }

        val emailVerificationModel = ResendVerificationCodeRequest(
            email = validatedState.email,
            isResent = true
        )


        viewModelScope.launch {
            _resendEmailVerificationUiState.value = ResendDVerificationEmailState.Loading
            val response =
                resendEmailVerificationCodeUseCase.invoke(emailVerificationModel) //ResendVerificationCodeRequest(formState.value.email,true)
            handleResendVerificationRequest(response)
        }
    }

    private fun handleResendVerificationRequest(response: RequestResult<ResendVerificationCodeResponse, DataError.ResendEmailVerificationCodeAuth>) {
        when (response) {
            is RequestResult.Success -> {
                _resendEmailVerificationUiState.value = ResendDVerificationEmailState.Success(response.data)
            }

            is RequestResult.Error -> {
                val errorMessage = when (response.error) {
                    DataError.ResendEmailVerificationCodeAuth.NOT_FOUND ->
                        "User not found"

                    DataError.ResendEmailVerificationCodeAuth.UNEXPECTED_ERROR ->
                        "Something went wrong. Please try again"

                    DataError.ResendEmailVerificationCodeAuth.NETWORK_TIMEOUT ->
                        "Couldn't connect. Please try again later"

                    DataError.ResendEmailVerificationCodeAuth.NETWORK_ERROR ->
                        "Check internet connection and try again"
                }
                _emailVerificationUiState.value = VerificationEmailUiState.Error(errorMessage)
            }

            else -> Unit
        }
    }

}