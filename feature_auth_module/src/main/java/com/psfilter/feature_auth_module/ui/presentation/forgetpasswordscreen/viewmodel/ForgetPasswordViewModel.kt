package com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.response.ResendVerificationCodeResponse
import com.psfilter.feature_auth_module.ui.AuthFields
import com.psfilter.feature_auth_module.ui.data.model.ForgetPasswordModel
import com.psfilter.feature_auth_module.ui.data.model.toResendVerificationCodeRequest
import com.psfilter.feature_auth_module.ui.domain.usecase.ResendEmailVerificationCodeUseCase
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateCodeForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateEmailForm
import com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.state.ForgetPasswordFormState
import com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.state.ForgetPasswordUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val resendEmailVerificationCodeUseCase: ResendEmailVerificationCodeUseCase
) : ViewModel() {

    private val _isFirstRequest = MutableStateFlow(true)
    val isFirstRequest: StateFlow<Boolean> = _isFirstRequest.asStateFlow()

    private val _isInitialCodeRequest = MutableStateFlow(false)
    val isInitialCodeRequest: StateFlow<Boolean> = _isInitialCodeRequest.asStateFlow()

    private val _requestVerificationCodeState =
        MutableStateFlow<ForgetPasswordUiState>(ForgetPasswordUiState.None)
    val requestVerificationCodeState: StateFlow<ForgetPasswordUiState> = _requestVerificationCodeState.asStateFlow()

    private val _showCodeField = MutableStateFlow(false)
    val showCodeField: StateFlow<Boolean> = _showCodeField.asStateFlow()

    private val _formState = mutableStateOf(ForgetPasswordFormState())
    val formState: State<ForgetPasswordFormState> = _formState

    fun resetForgetPasswordState() {
        _requestVerificationCodeState.value = ForgetPasswordUiState.None
    }

    fun setShowCodeField(show: Boolean) {
        _showCodeField.value = show
        if (!show) {
            _formState.value = _formState.value.copy(
                code = AuthFields.VerificationCode(""),
                codeError = null
            )
        }
    }

    fun updateEmail(newEmail: String) {
        _formState.value = _formState.value.copy(
            email = AuthFields.Email(newEmail),
            emailError = if (newEmail.isNotEmpty()) null else _formState.value.emailError
        )
    }

    fun validateEmailOnFocusLost() {
        val email = _formState.value.email
        if (email.raw.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                emailError = validateEmailForm(email.raw)
            )
        }
    }

    fun updateCode(newCode: String) {
        _formState.value = _formState.value.copy(
            code = AuthFields.VerificationCode(newCode),
            codeError = if (newCode.isNotEmpty()) null else _formState.value.codeError
        )
    }

    fun validateCodeOnFocusLost() {
        val code = _formState.value.code
        if (code.raw.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                codeError = validateCodeForm(code.raw)
            )
        }
    }

    private fun hasValidationErrors(): Boolean {
        val state = _formState.value
        return state.emailError != null ||
                state.codeError != null
    }


    private fun isEmailFilled(): Boolean {
        val state = _formState.value
        return state.email.raw.isNotEmpty()
    }

    private fun isCodeFilled(): Boolean {
        val state = _formState.value
        return state.code.raw.isNotEmpty()
    }

    fun isLoginButtonEnabled(): Boolean {
        val isEmailValid = isEmailFilled() && !hasValidationErrors()
        val isNotLoading = _requestVerificationCodeState.value != ForgetPasswordUiState.Loading

        return if (_showCodeField.value) {
            val isCodeValid = isCodeFilled() && validateCodeForm(_formState.value.code.raw) == null
            isEmailValid && isCodeValid && isNotLoading
        } else {
            isEmailValid && isNotLoading
        }
    }

    fun sendForgetPasswordCode(request: Boolean): Boolean {
        val currentState = _formState.value

        val emailError = validateEmailForm(currentState.email.raw)
        val codeError = if (showCodeField.value) validateCodeForm(currentState.code.raw) else null

        _formState.value = currentState.copy(
            emailError = emailError,
            codeError = codeError
        )

        if (emailError != null || (showCodeField.value && codeError != null)) {
            return false
        }

        viewModelScope.launch {
            _requestVerificationCodeState.value = ForgetPasswordUiState.Loading
            val registrationModel = ForgetPasswordModel(
                email = currentState.email.raw,
                isResent = request,
            )

            val forgetPasswordRequest = registrationModel.toResendVerificationCodeRequest()
            val response = resendEmailVerificationCodeUseCase.invoke(forgetPasswordRequest)

            handleSendVerificationCodeRequest(response)
        }

        return true
    }

    private fun handleSendVerificationCodeRequest(response: RequestResult<ResendVerificationCodeResponse, DataError.ResendEmailVerificationCodeAuth>) {
        when (response) {
            is RequestResult.Success -> {
                _requestVerificationCodeState.value = ForgetPasswordUiState.Success(response.data)
            }

            is RequestResult.Error -> {
                val error = response.error
                val errorMessage = when (error) {
                    DataError.ResendEmailVerificationCodeAuth.NOT_FOUND ->
                        "Account dos not exist"

                    DataError.ResendEmailVerificationCodeAuth.NETWORK_TIMEOUT ->
                        "Couldn't connect. Please try again later"

                    DataError.ResendEmailVerificationCodeAuth.NETWORK_ERROR ->
                        "Check internet connection and try again"

                    DataError.ResendEmailVerificationCodeAuth.UNEXPECTED_ERROR ->
                        "Something went wrong. Please try again"
                }
                _requestVerificationCodeState.value = ForgetPasswordUiState.Error(errorMessage, error)
            }

            else -> Unit
        }
    }
}