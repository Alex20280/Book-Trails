package com.project.booktrails.di

import com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.viewmodel.ForgetPasswordViewModel
import com.psfilter.feature_auth_module.ui.presentation.loginscreen.viewmodel.LoginViewModel
import com.psfilter.feature_auth_module.ui.presentation.signupscreen.viewmodel.SignUpViewModel
import com.psfilter.feature_auth_module.ui.presentation.splashscreen.viewmodel.SplashScreenViewModel
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.viewmodel.VerifyEmailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { VerifyEmailViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ForgetPasswordViewModel(get()) }
}