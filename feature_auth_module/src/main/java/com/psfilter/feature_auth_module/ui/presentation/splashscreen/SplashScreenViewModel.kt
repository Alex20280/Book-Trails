package com.psfilter.feature_auth_module.ui.presentation.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.core_module.UserPreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _isFirstTimeAppRun = MutableStateFlow<Boolean?>(null)
    val isFirstTimeAppRun: StateFlow<Boolean?> = _isFirstTimeAppRun

    init {
        viewModelScope.launch {
            _isFirstTimeAppRun.value = userPreferenceManager.isOnboardingSeen()
        }
    }

    fun setOnboardingSeen() {
        viewModelScope.launch {
            userPreferenceManager.saveOnboardingStatus(true)
            _isFirstTimeAppRun.value = true
        }
    }
}
