package com.psfilter.feature_auth_module.presentation.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.core_module.UserPreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel (
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _hasSeenOnboarding = MutableStateFlow<Boolean?>(null)
    val hasSeenOnboarding: StateFlow<Boolean?> = _hasSeenOnboarding

    init {
        viewModelScope.launch {
            _hasSeenOnboarding.value = userPreferenceManager.isOnboardingSeen()
        }
    }

    fun setOnboardingSeen() {
        viewModelScope.launch {
            userPreferenceManager.saveOnboardingStatus(true)
            _hasSeenOnboarding.value = true
            Log.d("OnBoardingStatus", _hasSeenOnboarding.value.toString())
        }
    }
}