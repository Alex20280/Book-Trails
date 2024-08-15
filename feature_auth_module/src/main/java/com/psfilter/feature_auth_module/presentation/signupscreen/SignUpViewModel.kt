package com.psfilter.feature_auth_module.presentation.signupscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.psfilter.feature_auth_module.domain.usecase.EmailSignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val emailSignInUseCase: EmailSignUpUseCase
) : ViewModel() {

    private val _registrationResult =
        MutableStateFlow<RequestResult<Boolean, DataError>>(RequestResult.None)
    val registrationResult: StateFlow<RequestResult<Boolean, DataError>> =
        _registrationResult.asStateFlow()

    fun setRegistrationResult(result: RequestResult<Boolean, DataError>) {
        _registrationResult.value = result
    }

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val signInResponse = emailSignInUseCase.registerUser(email, password)
            checkEmailRegistrationResponse(signInResponse)
        }
    }
    private fun checkEmailRegistrationResponse(response: RequestResult<Task<AuthResult>, DataError.Firebase>) {
        when (response) {
            is RequestResult.Success -> {
                response.data.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            emailSignInUseCase.createFirestoreAccount(task)
                            _registrationResult.value = RequestResult.Success(true)
                        }
                    }
                }
            }

            is RequestResult.Error -> {
                handlePasswordError(response.error)
            }

            is RequestResult.Loading -> Unit

            is RequestResult.None -> Unit
        }
    }


    private fun handlePasswordError(error: DataError) {
        val message = when (error) {
            DataError.Firebase.REGISTRATION_PROBLEM -> "Registration problem"
            DataError.Firebase.LOGIN_PROBLEM -> "Login problem"
            DataError.Firebase.RESET_PROBLEM -> "Reset Password problem"
            DataError.Firebase.UID_PROBLEM -> "No uid"
            DataError.Firebase.FIREBASE_AUTH_EXCEPTION -> "Authentication problem"
            DataError.Firebase.UNEXPECTED_ERROR -> "Unexpected Error"
            DataError.Gmail.AUTH_PROBLEM -> "Authentication problem"
        }
        _errorMessage.value = message
    }

}



