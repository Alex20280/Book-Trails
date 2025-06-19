package com.psfilter.feature_auth_module.ui.presentation.createnewpassword.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.response.SetNewPasswordResponse
import com.psfilter.feature_auth_module.ui.AuthFields
import com.psfilter.feature_auth_module.ui.data.model.SetNewPasswordModel
import com.psfilter.feature_auth_module.ui.data.model.toSetNewPasswordRequest
import com.psfilter.feature_auth_module.ui.domain.usecase.SetNewPasswordUseCase
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateCodeForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateEmailForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validatePasswordConfirmationForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validatePasswordForm
import com.psfilter.feature_auth_module.ui.presentation.createnewpassword.state.SetNewPasswordFormState
import com.psfilter.feature_auth_module.ui.presentation.createnewpassword.state.SetNewPassPasswordUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SetNewPasswordViewModel(
    private val setNewPasswordUseCase: SetNewPasswordUseCase
) : ViewModel() {

    private val _forgetPasswordState =
        MutableStateFlow<SetNewPassPasswordUiState>(SetNewPassPasswordUiState.None)
    val forgetPasswordState: StateFlow<SetNewPassPasswordUiState> = _forgetPasswordState.asStateFlow()

    private val _formState = mutableStateOf(SetNewPasswordFormState())
    val formState: State<SetNewPasswordFormState> = _formState

    fun setInitialEmailAndCode(email: String, code: String) {
        _formState.value = _formState.value.copy(
            email = AuthFields.Email(email),
            verificationCode = AuthFields.VerificationCode(code)
        )
    }

    fun updateCode(newCode: String) {
        _formState.value = _formState.value.copy(
            verificationCode = AuthFields.VerificationCode(newCode),
            verificationCodeError = if (newCode.isNotEmpty()) null else _formState.value.verificationCodeError
        )
    }

    fun updatePassword(newPassword: String) {
        val currentState = _formState.value
        _formState.value = currentState.copy(
            password = AuthFields.Password(newPassword),
            passwordError = if (newPassword.isNotEmpty()) null else currentState.passwordError,
            confirmPasswordError = if (newPassword.isNotEmpty() && currentState.confirmPassword.raw.isNotEmpty())
                null else currentState.confirmPasswordError
        )
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _formState.value = _formState.value.copy(
            confirmPassword = AuthFields.ConfirmPassword(newConfirmPassword),
            confirmPasswordError = if (newConfirmPassword.isNotEmpty()) null else _formState.value.confirmPasswordError
        )
    }


    fun validateCodeOnFocusLost() {
        val name = _formState.value.verificationCode
        if (name.raw.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                verificationCodeError = validateCodeForm(name.raw)
            )
        }
    }

    fun validatePasswordOnFocusLost() {
        val password = _formState.value.password
        if (password.raw.isNotEmpty()) {
            val currentState = _formState.value
            _formState.value = currentState.copy(
                passwordError = validatePasswordForm(password.raw),
                confirmPasswordError = if (currentState.confirmPassword.raw.isNotEmpty()) {
                    validatePasswordConfirmationForm(password.raw, currentState.confirmPassword.raw)
                } else currentState.confirmPasswordError
            )
        }
    }

    fun validateConfirmPasswordOnFocusLost() {
        val currentState = _formState.value
        if (currentState.confirmPassword.raw.isNotEmpty()) {
            _formState.value = currentState.copy(
                confirmPasswordError = validatePasswordConfirmationForm(
                    currentState.password.raw,
                    currentState.confirmPassword.raw
                )
            )
        }
    }

    private fun hasValidationErrors(): Boolean {
        val state = _formState.value
        return state.emailError != null ||
                state.verificationCodeError != null ||
                state.passwordError != null ||
                state.confirmPasswordError != null
    }

    private fun areAllFieldsFilled(): Boolean {
        val state = _formState.value
        return state.email.raw.isNotEmpty() &&
                state.verificationCode.raw.isNotEmpty() &&
                state.password.raw.isNotEmpty() &&
                state.confirmPassword.raw.isNotEmpty()
    }

    fun isSaveButtonEnabled(): Boolean {
        return areAllFieldsFilled() && !hasValidationErrors() && _forgetPasswordState.value != SetNewPassPasswordUiState.Loading
    }

    fun setNewPassword() {
        val currentState = _formState.value

        val validatedState = currentState.copy(
            emailError = validateEmailForm(currentState.email.raw),
            verificationCodeError = validateCodeForm(currentState.verificationCode.raw),
            passwordError = validatePasswordForm(currentState.password.raw),
            confirmPasswordError = validatePasswordConfirmationForm(
                currentState.password.raw,
                currentState.confirmPassword.raw
            )
        )

        _formState.value = validatedState

        if (hasValidationErrors()) {
            return
        }

        val setNewPasswordModel = SetNewPasswordModel(
            email = validatedState.email.raw,
            newPassword = validatedState.password.raw,
            code = validatedState.verificationCode.raw,
        )

        viewModelScope.launch {
            _forgetPasswordState.value = SetNewPassPasswordUiState.Loading
            val registrationRequest = setNewPasswordModel.toSetNewPasswordRequest()
            val response = setNewPasswordUseCase.invoke(registrationRequest)
            handleRegistrationResponse(response)
        }
    }

    private fun handleRegistrationResponse(response: RequestResult<SetNewPasswordResponse, DataError.SetNewPasswordAuth>) {
        when (response) {
            is RequestResult.Success -> {
                _forgetPasswordState.value = SetNewPassPasswordUiState.Success(response.data)
            }

            is RequestResult.Error -> {
                val error = response.error
                val errorMessage = when (error) {
                    DataError.SetNewPasswordAuth.INCORRECT_PASSWORD ->
                        "Please enter a valid email address"

                    DataError.SetNewPasswordAuth.USER_NOT_FOUND ->
                        "User not found"

                    DataError.SetNewPasswordAuth.UNEXPECTED_ERROR ->
                        "Something went wrong. Please try again"

                    DataError.SetNewPasswordAuth.NETWORK_TIMEOUT ->
                        "Couldn't connect. Please try again later"

                    DataError.SetNewPasswordAuth.NETWORK_ERROR ->
                        "Check internet connection and try again"

                    DataError.SetNewPasswordAuth.INVALID_RESET_CODE ->
                        "Invalid reset code"
                }
                _forgetPasswordState.value = SetNewPassPasswordUiState.Error(errorMessage, error)
            }

            else -> Unit
        }
    }

}