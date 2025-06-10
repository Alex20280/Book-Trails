package com.project.booktrails.di

import com.psfilter.feature_auth_module.ui.presentation.signupscreen.viewmodel.SignUpViewModel
import com.psfilter.feature_auth_module.ui.presentation.splashscreen.SplashScreenViewModel
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.viewmodel.VerifyEmailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { VerifyEmailViewModel() }
}