package com.psfilter.feature_auth_module.presentation.loginscreen

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.firebase.auth.AuthResult
import com.psfilter.feature_auth_module.domain.usecase.EmailLogInUseCase
import com.psfilter.feature_auth_module.domain.usecase.GmailLogInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val emailSignInUseCase: EmailLogInUseCase,
    private val gmailLogInUseCase: GmailLogInUseCase
) : ViewModel() {

    private val _logInResultState =
        MutableStateFlow<RequestResult<Boolean, DataError>>(RequestResult.None)
    val logInResultState: StateFlow<RequestResult<Boolean, DataError>> = _logInResultState.asStateFlow()
    fun setLogInResultState(result: RequestResult<Boolean, DataError>) {
        _logInResultState.value = result
    }

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun emailPasswordSignIn(email: String, password: String) {
        _logInResultState.value = RequestResult.Loading
        viewModelScope.launch {
            val signInResponse = emailSignInUseCase.loginEmailPassword(email, password)
            checkEmailPasswordLoginResponse(signInResponse)
        }
    }

    private fun checkEmailPasswordLoginResponse(signInResponse: RequestResult<AuthResult, DataError.Firebase>) {
        viewModelScope.launch {
            when (signInResponse) {
                is RequestResult.Success -> {
                    _logInResultState.value = RequestResult.Success(true)
                }

                is RequestResult.Error -> {
                    _logInResultState.value = RequestResult.Error(signInResponse.error)
                    handlePasswordError(signInResponse.error)
                }

                is RequestResult.Loading -> Unit

                is RequestResult.None -> Unit
            }
        }
    }

    private fun handlePasswordError(error: DataError.Firebase) {
        val message = when (error) {
            DataError.Firebase.REGISTRATION_PROBLEM -> "Registration problem"
            DataError.Firebase.LOGIN_PROBLEM -> "Please create an account first"
            DataError.Firebase.RESET_PROBLEM -> "Reset Password problem"
            DataError.Firebase.UID_PROBLEM -> "No uid"
            DataError.Firebase.FIREBASE_AUTH_EXCEPTION -> "Authentication problem"
            DataError.Firebase.UNEXPECTED_ERROR -> "Unexpected Error"
        }
        _errorMessage.value = message
    }

    fun getGoogleSignUpIntent(): Intent {
        return gmailLogInUseCase.logInWithGmail()
    }

    fun handleActivityResult(result: ActivityResult) {
        viewModelScope.launch {
            if (result.resultCode == Activity.RESULT_OK) {
                _logInResultState.value = RequestResult.Loading
                val signInResult = gmailLogInUseCase.signIn(result)
                handleAuthResult(signInResult)
            }
        }
    }

    private fun handleAuthResult(signInResult: RequestResult<Unit, DataError.Firebase>) {
        when (signInResult) {
            is RequestResult.Success -> {
                _logInResultState.value = RequestResult.Success(true)
            }

            is RequestResult.Error -> {
                _logInResultState.value = RequestResult.Error(DataError.Gmail.AUTH_PROBLEM)
            }

            is RequestResult.Loading -> Unit
            is RequestResult.None -> Unit
        }
    }


}