package com.booktrails.feature_profile_module.profilescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psfilter.feature_auth_module.domain.usecase.EmailLogOutUseCase
import kotlinx.coroutines.launch

class ProfileScreenViewModel (
    private val emailLogOutUseCase: EmailLogOutUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            emailLogOutUseCase.logOut()
        }
    }
}