package com.psfilter.feature_auth_module.ui.presentation.signupscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.response.EmailRegistrationResponse
import com.psfilter.feature_auth_module.ui.AuthFields
import com.psfilter.feature_auth_module.ui.data.model.EmailRegistrationModel
import com.psfilter.feature_auth_module.ui.data.model.toEmailRegistrationRequest
import com.psfilter.feature_auth_module.ui.domain.usecase.RegisterWithEmailAndPasswordUseCase
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateEmailForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validateNameForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validatePasswordConfirmationForm
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils.validatePasswordForm
import com.psfilter.feature_auth_module.ui.presentation.signupscreen.state.RegistrationFormState
import com.psfilter.feature_auth_module.ui.presentation.signupscreen.state.RegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel (
    private val registerWithEmailAndPasswordUseCase: RegisterWithEmailAndPasswordUseCase
) : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.None)
    val registrationState: StateFlow<RegistrationUiState> = _registrationState.asStateFlow()

    private val _formState = mutableStateOf(RegistrationFormState())
    val formState: State<RegistrationFormState> = _formState


    fun updateEmail(newEmail: String) {
        _formState.value = _formState.value.copy(
            email = AuthFields.Email(newEmail),
            emailError = if (newEmail.isNotEmpty()) null else _formState.value.emailError
        )
    }

    fun updateName(newName: String) {
        _formState.value = _formState.value.copy(
            name = AuthFields.Name(newName),
            nameError = if (newName.isNotEmpty()) null else _formState.value.nameError
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

    fun validateEmailOnFocusLost() {
        val email = _formState.value.email
        if (email.raw.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                emailError = validateEmailForm(email.raw)
            )
        }
    }

    fun validateNameOnFocusLost() {
        val name = _formState.value.name
        if (name.raw.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                nameError = validateNameForm(name.raw)
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
                confirmPasswordError = validatePasswordConfirmationForm(currentState.password.raw, currentState.confirmPassword.raw)
            )
        }
    }

    private fun hasValidationErrors(): Boolean {
        val state = _formState.value
        return state.emailError != null ||
                state.nameError != null ||
                state.passwordError != null ||
                state.confirmPasswordError != null
    }

    private fun areAllFieldsFilled(): Boolean {
        val state = _formState.value
        return state.email.raw.isNotEmpty() &&
                state.name.raw.isNotEmpty() &&
                state.password.raw.isNotEmpty() &&
                state.confirmPassword.raw.isNotEmpty()
    }

    fun isRegistrationButtonEnabled(): Boolean {
        return areAllFieldsFilled() && !hasValidationErrors() && _registrationState.value != RegistrationUiState.Loading
    }

    fun registerNewUser() {
        val currentState = _formState.value

        val validatedState = currentState.copy(
            emailError = validateEmailForm(currentState.email.raw),
            nameError = validateNameForm(currentState.name.raw),
            passwordError = validatePasswordForm(currentState.password.raw),
            confirmPasswordError = validatePasswordConfirmationForm(currentState.password.raw, currentState.confirmPassword.raw)
        )

        _formState.value = validatedState

        if (hasValidationErrors()) {
            return
        }

        val registrationModel = EmailRegistrationModel(
            email = validatedState.email.raw,
            password = validatedState.password.raw,
            name = validatedState.name.raw,
            confirmPassword = validatedState.confirmPassword.raw
        )

        viewModelScope.launch {
            _registrationState.value = RegistrationUiState.Loading
            val registrationRequest = registrationModel.toEmailRegistrationRequest()
            val response = registerWithEmailAndPasswordUseCase.invoke(registrationRequest)
            handleRegistrationResponse(response)
        }
    }

    private fun handleRegistrationResponse(response: RequestResult<EmailRegistrationResponse, DataError.EmailPasswordRegistration>) {
        when (response) {
            is RequestResult.Success -> {
                _registrationState.value = RegistrationUiState.Success(response.data)
            }

            is RequestResult.Error -> {
                val error = response.error
                val errorMessage = when (error) {
                    DataError.EmailPasswordRegistration.INCORRECT_EMAIL_FORMAT ->
                        "Please enter a valid email address"

                    DataError.EmailPasswordRegistration.ACCOUNT_ALREADY_EXISTS_BUT_NOT_VERIFIED ->
                        "Account already exists but not verified"

                    DataError.EmailPasswordRegistration.UNEXPECTED_ERROR ->
                        "Something went wrong. Please try again"

                    DataError.EmailPasswordRegistration.NETWORK_TIMEOUT ->
                        "Couldn't connect. Please try again later"

                    DataError.EmailPasswordRegistration.NETWORK_ERROR ->
                        "Check internet connection and try again"

                    DataError.EmailPasswordRegistration.ACCOUNT_ALREADY_IN_USE ->
                        "Account already exists"
                }
                _registrationState.value = RegistrationUiState.Error(errorMessage, error)
            }

            else -> Unit
        }
    }

}
