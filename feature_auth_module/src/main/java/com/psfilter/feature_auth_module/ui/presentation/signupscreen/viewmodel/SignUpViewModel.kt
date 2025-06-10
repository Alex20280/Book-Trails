package com.psfilter.feature_auth_module.ui.presentation.signupscreen.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network_module.errorhandling.DataError
import com.network_module.errorhandling.RequestResult
import com.network_module.model.response.EmailRegistrationResponse
import com.psfilter.feature_auth_module.ui.data.model.EmailRegistrationModel
import com.psfilter.feature_auth_module.ui.data.model.toEmailRegistrationRequest
import com.psfilter.feature_auth_module.ui.domain.usecase.RegisterWithEmailAndPasswordUseCase
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils
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
            email = newEmail,
            emailError = if (newEmail.isNotEmpty()) null else _formState.value.emailError
        )
    }

    fun updateName(newName: String) {
        _formState.value = _formState.value.copy(
            name = newName,
            nameError = if (newName.isNotEmpty()) null else _formState.value.nameError
        )
    }

    fun updatePassword(newPassword: String) {
        val currentState = _formState.value
        _formState.value = currentState.copy(
            password = newPassword,
            passwordError = if (newPassword.isNotEmpty()) null else currentState.passwordError,
            confirmPasswordError = if (newPassword.isNotEmpty() && currentState.confirmPassword.isNotEmpty())
                null else currentState.confirmPasswordError
        )
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _formState.value = _formState.value.copy(
            confirmPassword = newConfirmPassword,
            confirmPasswordError = if (newConfirmPassword.isNotEmpty()) null else _formState.value.confirmPasswordError
        )
    }

    fun validateEmailOnFocusLost() {
        val email = _formState.value.email
        if (email.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                emailError = validateEmail(email)
            )
        }
    }

    fun validateNameOnFocusLost() {
        val name = _formState.value.name
        if (name.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                nameError = validateName(name)
            )
        }
    }

    fun validatePasswordOnFocusLost() {
        val password = _formState.value.password
        if (password.isNotEmpty()) {
            val currentState = _formState.value
            _formState.value = currentState.copy(
                passwordError = validatePassword(password),
                confirmPasswordError = if (currentState.confirmPassword.isNotEmpty()) {
                    validatePasswordConfirmation(password, currentState.confirmPassword)
                } else currentState.confirmPasswordError
            )
        }
    }

    fun validateConfirmPasswordOnFocusLost() {
        val currentState = _formState.value
        if (currentState.confirmPassword.isNotEmpty()) {
            _formState.value = currentState.copy(
                confirmPasswordError = validatePasswordConfirmation(currentState.password, currentState.confirmPassword)
            )
        }
    }

    private fun validateEmail(email: String): String? {
        return when (val validation = ValidationUtils.validateEmail(email)) {
            is ValidationUtils.ValidationResult.Error -> validation.message
            is ValidationUtils.ValidationResult.Success -> null
        }
    }

    private fun validateName(name: String): String? {
        return when (val validation = ValidationUtils.validateName(name)) {
            is ValidationUtils.ValidationResult.Error -> validation.message
            is ValidationUtils.ValidationResult.Success -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when (val validation = ValidationUtils.validatePassword(password)) {
            is ValidationUtils.ValidationResult.Error -> validation.message
            is ValidationUtils.ValidationResult.Success -> null
        }
    }

    private fun validatePasswordConfirmation(password: String, confirmPassword: String): String? {
        return when (val validation = ValidationUtils.validatePasswordConfirmation(password, confirmPassword)) {
            is ValidationUtils.ValidationResult.Error -> validation.message
            is ValidationUtils.ValidationResult.Success -> null
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
        return state.email.isNotEmpty() &&
                state.name.isNotEmpty() &&
                state.password.isNotEmpty() &&
                state.confirmPassword.isNotEmpty()
    }

    fun isRegistrationButtonEnabled(): Boolean {
        return areAllFieldsFilled() && !hasValidationErrors() && _registrationState.value != RegistrationUiState.Loading
    }

    fun registerNewUser() {
        val currentState = _formState.value

        // Полная валидация перед отправкой
        val validatedState = currentState.copy(
            emailError = validateEmail(currentState.email),
            nameError = validateName(currentState.name),
            passwordError = validatePassword(currentState.password),
            confirmPasswordError = validatePasswordConfirmation(currentState.password, currentState.confirmPassword)
        )

        _formState.value = validatedState

        if (hasValidationErrors()) {
            return
        }

        val registrationModel = EmailRegistrationModel(
            email = validatedState.email,
            password = validatedState.password,
            name = validatedState.name,
            confirmPassword = validatedState.confirmPassword
        )

        viewModelScope.launch {
            _registrationState.value = RegistrationUiState.Loading
            Log.d("MyLosdingstate", " when loading: " +_registrationState.value.toString())
            val registrationRequest = registrationModel.toEmailRegistrationRequest()
            val response = registerWithEmailAndPasswordUseCase.invoke(registrationRequest)
            handleRegistrationResponse(response)
        }
    }

    private fun handleRegistrationResponse(response: RequestResult<EmailRegistrationResponse, DataError.EmailPasswordAuth>) {
        when (response) {
            is RequestResult.Success -> {
                _registrationState.value = RegistrationUiState.Success(response.data)
                Log.d("MyLosdingstate", " when success: " + _registrationState.value.toString())
            }

            is RequestResult.Error -> {
                val errorMessage = when (response.error) {
                    DataError.EmailPasswordAuth.INCORRECT_EMAIL_FORMAT ->
                        "Please enter a valid email address"

                    DataError.EmailPasswordAuth.ACCOUNT_ALREADY_EXISTS ->
                        "Account already exists"

                    DataError.EmailPasswordAuth.UNEXPECTED_ERROR ->
                        "Something went wrong. Please try again."
                }
                _registrationState.value = RegistrationUiState.Error(errorMessage)
            }

            else -> Unit
        }
    }

    private fun handleRegistrationError(exception: Exception) {
        // Обработка сетевых ошибок
    }

}
