package com.project.booktrails.di

import com.google.firebase.auth.FirebaseAuth
import com.psfilter.feature_auth_module.data.EmailAuthRepositoryImpl
import com.psfilter.feature_auth_module.domain.EmailAuthRepository
import com.psfilter.feature_auth_module.domain.service.EmailAndPasswordValidator
import com.psfilter.feature_auth_module.domain.usecase.EmailPasswordResetUseCase
import com.psfilter.feature_auth_module.domain.usecase.EmailSignInUseCase
import com.psfilter.feature_auth_module.domain.usecase.EmailSignOutUseCase
import com.psfilter.feature_auth_module.domain.usecase.GetEmailUserUidUseCase
import com.psfilter.feature_auth_module.presentation.loginscreen.LoginViewModel
import com.psfilter.feature_auth_module.presentation.onboarding.OnboardingViewModel
import com.psfilter.feature_auth_module.presentation.signupscreen.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { FirebaseAuth.getInstance() }

    single<EmailAuthRepository> { EmailAuthRepositoryImpl(get()) }

    single { EmailPasswordResetUseCase(get()) }

    single { EmailSignInUseCase(get()) }

    single { EmailSignOutUseCase(get()) }

    single { GetEmailUserUidUseCase(get()) }

    single { EmailAndPasswordValidator() }

    viewModel { OnboardingViewModel(get()) }
    viewModel { LoginViewModel() }
    viewModel { SignUpViewModel(get(), get()) }
}