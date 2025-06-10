package com.psfilter.feature_auth_module.ui.presentation.verifyemail.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.psfilter.feature_auth_module.ui.presentation.ValidationUtils
import com.psfilter.feature_auth_module.ui.presentation.signupscreen.state.RegistrationUiState
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.ConfirmEmailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VerifyEmailViewModel () : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.None)
    val registrationState: StateFlow<RegistrationUiState> = _registrationState.asStateFlow()

    private val _formState = mutableStateOf(ConfirmEmailState())
    val formState: State<ConfirmEmailState> = _formState

    fun validateEmailOnFocusLost() {
        val email = _formState.value.email
        if (email.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                emailError = validateEmail(email)
            )
        }
    }

    private fun validateEmail(email: String): String? {
        return when (val validation = ValidationUtils.validateEmail(email)) {
            is ValidationUtils.ValidationResult.Error -> validation.message
            is ValidationUtils.ValidationResult.Success -> null
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

    fun resendCode() {
        TODO("Not yet implemented")
    }

}