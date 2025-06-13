package com.psfilter.feature_auth_module.ui.presentation.loginscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.response.LoginWithEmailPassResponse
import com.psfilter.feature_auth_module.ui.AuthFields
import com.psfilter.feature_auth_module.ui.data.model.EmailLoginModel
import com.psfilter.feature_auth_module.ui.data.model.toLoginWithEmailPassRequest
import com.psfilter.feature_auth_module.ui.domain.usecase.LoginWithEmailPasswordUseCase
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateEmailForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validatePasswordForm
import com.psfilter.feature_auth_module.ui.presentation.loginscreen.state.LoginFormState
import com.psfilter.feature_auth_module.ui.presentation.loginscreen.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginWithEmailPasswordUseCase: LoginWithEmailPasswordUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.None)
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    private val _formState = mutableStateOf(LoginFormState())
    val formState: State<LoginFormState> = _formState


    fun updateEmail(newEmail: String) {
        _formState.value = _formState.value.copy(
            email = AuthFields.Email(newEmail),
            emailError = if (newEmail.isNotEmpty()) null else _formState.value.emailError
        )
    }

    fun updatePassword(newPassword: String) {
        val currentState = _formState.value
        _formState.value = currentState.copy(
            password = AuthFields.Password(newPassword),
            passwordError = if (newPassword.isNotEmpty()) null else currentState.passwordError
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

    fun validatePasswordOnFocusLost() {
        val password = _formState.value.password
        if (password.raw.isNotEmpty()) {
            val currentState = _formState.value
            _formState.value = currentState.copy(
                passwordError = validatePasswordForm(password.raw)
            )
        }
    }

    private fun hasValidationErrors(): Boolean {
        val state = _formState.value
        return state.emailError != null ||
                state.passwordError != null
    }

    private fun areAllFieldsFilled(): Boolean {
        val state = _formState.value
        return state.email.raw.isNotEmpty() &&
                state.password.raw.isNotEmpty()
    }

    fun isLoginButtonEnabled(): Boolean {
        return areAllFieldsFilled() && !hasValidationErrors() && _loginState.value != LoginUiState.Loading
    }

    fun loginUser() {
        val currentState = _formState.value

        val validatedState = currentState.copy(
            emailError = validateEmailForm(currentState.email.raw),
            passwordError = validatePasswordForm(currentState.password.raw),
        )

        _formState.value = validatedState

        if (hasValidationErrors()) {
            return
        }

        val registrationModel = EmailLoginModel(
            email = validatedState.email.raw,
            password = validatedState.password.raw,
        )

        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            val loginRequest = registrationModel.toLoginWithEmailPassRequest()
            val response = loginWithEmailPasswordUseCase.invoke(loginRequest)
            handleLoginResponse(response)
        }
    }

    private fun handleLoginResponse(response: RequestResult<LoginWithEmailPassResponse, DataError.EmailPasswordAuth>) {
        when (response) {
            is RequestResult.Success -> {
                _loginState.value = LoginUiState.Success(response.data)
            }

            is RequestResult.Error -> {
                val error = response.error
                val errorMessage = when (error) {

                    DataError.EmailPasswordAuth.UNAUTHORIZED ->
                        "Email or password are incorrect"

                    DataError.EmailPasswordAuth.EMAIL_NOT_VERIFIED ->
                        "Email not verified"

                    DataError.EmailPasswordAuth.UNEXPECTED_ERROR ->
                        "Something went wrong. Please try again"

                    DataError.EmailPasswordAuth.NETWORK_TIMEOUT ->
                        "Couldn't connect. Please try again later"

                    DataError.EmailPasswordAuth.NETWORK_ERROR ->
                        "Check internet connection and try again"

                    DataError.EmailPasswordAuth.NOT_FOUND ->
                        "Account dos not exist"
                }
                _loginState.value = LoginUiState.Error(errorMessage, error)
            }

            else -> Unit
        }
    }
}