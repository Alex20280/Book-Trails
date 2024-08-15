package com.booktrails.feature_profile_module.profilescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.feature_profile_module.profilescreen.state.LogoutState
import com.google.firebase.auth.GoogleAuthProvider
import com.psfilter.feature_auth_module.domain.usecase.EmailLogOutUseCase
import com.psfilter.feature_auth_module.domain.usecase.GmailLogOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val emailLogOutUseCase: EmailLogOutUseCase,
    private val gmailLogOutUseCase: GmailLogOutUseCase
) : ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.NONE)
    val logoutState: StateFlow<LogoutState> = _logoutState.asStateFlow()
    fun setLogoutState(logoutState: LogoutState){
        _logoutState.value = logoutState
    }

    fun logout() {
        viewModelScope.launch() {
            try {
                val providers: List<String>? = emailLogOutUseCase.getCurrentUserProvider()
                val isGmailUser = providers?.contains(GoogleAuthProvider.PROVIDER_ID)
                if (isGmailUser == true) {
                    gmailLogOutUseCase.logOutWithGoogle()
                }
                emailLogOutUseCase.logOut()
                _logoutState.value = LogoutState.SUCCESS
            } catch (e: Exception) {
                Log.e("LogoutError", "Error during logout: ${e.message}")
                _logoutState.value = LogoutState.ERROR
            }
        }

    }
}