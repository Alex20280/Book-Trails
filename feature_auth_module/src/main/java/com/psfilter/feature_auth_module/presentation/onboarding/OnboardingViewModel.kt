package com.psfilter.feature_auth_module.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.core_module.service.UserPreferenceManager
import com.psfilter.feature_auth_module.domain.usecase.EmailLogInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel (
    private val emailLogInUseCase: EmailLogInUseCase,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _hasSeenOnboarding = MutableStateFlow<Boolean?>(null)
    val hasSeenOnboarding: StateFlow<Boolean?> = _hasSeenOnboarding

    private val _isUserSignedIn = MutableStateFlow<Boolean>(false)
    val isUserSignedIn: StateFlow<Boolean> = _isUserSignedIn

    init {
        viewModelScope.launch {
            val state = emailLogInUseCase.auth()
            handleSignInState(state)
            _hasSeenOnboarding.value = userPreferenceManager.isOnboardingSeen()
        }
    }

    private fun handleSignInState(state: RequestResult<Unit, DataError.Firebase>) {
        when (state) {
            is RequestResult.Success -> {
                _isUserSignedIn.value = true
            }
            is RequestResult.Error -> {
                _isUserSignedIn.value = false
            }
            else -> false
        }
    }

    fun setOnboardingSeen() {
        viewModelScope.launch {
            userPreferenceManager.saveOnboardingStatus(true)
            _hasSeenOnboarding.value = true
        }
    }
}