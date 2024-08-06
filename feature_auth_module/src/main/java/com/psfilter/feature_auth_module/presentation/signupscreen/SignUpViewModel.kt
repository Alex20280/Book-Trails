package com.psfilter.feature_auth_module.presentation.signupscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.psfilter.feature_auth_module.domain.service.EmailAndPasswordValidator
import com.psfilter.feature_auth_module.domain.usecase.EmailSignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val emailAndPasswordValidator: EmailAndPasswordValidator,
    private val emailSignInUseCase: EmailSignInUseCase
) : ViewModel() {

    private val _registrationResult = MutableStateFlow<RequestResult<Boolean, DataError>>(RequestResult.Success(false))
    val registrationResult: StateFlow<RequestResult<Boolean, DataError>> = _registrationResult.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun registerUser(email: String, password: String, confirmPasswordText: String) {
        viewModelScope.launch {
            when (val validationResult = emailAndPasswordValidator.validateRegistration(email, password, confirmPasswordText)) {
                is RequestResult.Success -> {
                    val signInResponse = emailSignInUseCase.registerUser(email, password)
                    checkEmailRegistrationResponse(signInResponse)
                }

                is RequestResult.Error -> {
                    handlePasswordError(validationResult.error)
                }

                is RequestResult.Loading -> Unit
            }
        }
    }


    private fun handlePasswordError(error: DataError) {
        val message = when (error) {
            DataError.AuthenticationError.LOGIN_IS_NOT_CORRECT -> "Login is incorrect"
            DataError.AuthenticationError.PASSWORDS_NOT_MATCH -> "Passwords not match"
            DataError.AuthenticationError.PASSWORD_IS_TOO_SHORT -> "Password is too short"
            DataError.AuthenticationError.PASSWORD_NOT_HAVE_UPPER_CASE_LETTER -> "Password should have at least 1 upper case letter"
            DataError.AuthenticationError.PASSWORD_NOT_HAVE_DIGITS -> "Password should have at least 1 digit"
            DataError.AuthenticationError.PASSWORD_NOT_SPECIAL_CHARACTERS -> "Password should have at least 1 special character"
            DataError.Firebase.REGISTRATION_PROBLEM -> "Registration problem"
            DataError.Firebase.LOGIN_PROBLEM -> "Login problem"
            DataError.Firebase.RESET_PROBLEM -> "Reset Password problem"
            DataError.Firebase.UID_PROBLEM -> "No uid"
        }
        _errorMessage.value = message
    }


    private fun checkEmailRegistrationResponse(response: RequestResult<Task<AuthResult>,  DataError.Firebase>) {
        viewModelScope.launch {
            when (response) {
                is RequestResult.Success -> {
                    _registrationResult.value = RequestResult.Success(true)
                }
                is RequestResult.Error -> {
                    handlePasswordError(response.error)
                }
                is RequestResult.Loading -> Unit
            }
        }
    }


}



