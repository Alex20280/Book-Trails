package com.project.booktrails.di

import com.psfilter.feature_auth_module.ui.onboarding.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        OnboardingViewModel(get())
    }
}