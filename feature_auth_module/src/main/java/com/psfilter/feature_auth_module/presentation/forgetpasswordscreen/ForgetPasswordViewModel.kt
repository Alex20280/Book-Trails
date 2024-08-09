package com.psfilter.feature_auth_module.presentation.forgetpasswordscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.psfilter.feature_auth_module.domain.usecase.EmailPasswordResetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgetPasswordViewModel (
    private val forgetPasswordUseCase: EmailPasswordResetUseCase
) : ViewModel() {

    private val _resetPasswordResult = MutableStateFlow<RequestResult<Boolean, DataError>>(
        RequestResult.None)
    val resetPasswordResult: StateFlow<RequestResult<Boolean, DataError>> = _resetPasswordResult.asStateFlow()
    fun setResetPasswordResult(result: RequestResult<Boolean, DataError>) {
        _resetPasswordResult.value = result
    }

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun resetPassword(email: String) {
        viewModelScope.launch {
            val signInResponse = forgetPasswordUseCase.resetPassword(email)
            checkEmailRegistrationResponse(signInResponse)
        }
    }

    private fun checkEmailRegistrationResponse(response: RequestResult<Task<Void>, DataError.Firebase>) {
        viewModelScope.launch {
            when (response) {
                is RequestResult.Success -> {
                    _resetPasswordResult.value = RequestResult.Success(true)
                }
                is RequestResult.Error -> {
                    handlePasswordError(response.error)
                }
                is RequestResult.Loading -> Unit

                is RequestResult.None -> Unit
            }
        }
    }

    private fun handlePasswordError(error: DataError) {  //TODO: check error types
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
