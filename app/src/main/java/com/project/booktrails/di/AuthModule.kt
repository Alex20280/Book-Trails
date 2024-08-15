package com.project.booktrails.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.credentials.CredentialManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.project.booktrails.BuildConfig
import com.psfilter.feature_auth_module.data.EmailAuthRepositoryImpl
import com.psfilter.feature_auth_module.data.GoogleAuthRepositoryImpl
import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository
import com.psfilter.feature_auth_module.domain.repository.GoogleAuthRepository
import com.psfilter.feature_auth_module.domain.usecase.EmailPasswordResetUseCase
import com.psfilter.feature_auth_module.domain.usecase.EmailSignUpUseCase
import com.psfilter.feature_auth_module.domain.usecase.EmailLogInUseCase
import com.psfilter.feature_auth_module.domain.usecase.EmailLogOutUseCase
import com.psfilter.feature_auth_module.domain.usecase.GmailLogInUseCase
import com.psfilter.feature_auth_module.domain.usecase.GmailLogOutUseCase
import com.psfilter.feature_auth_module.presentation.forgetpasswordscreen.ForgetPasswordViewModel
import com.psfilter.feature_auth_module.presentation.loginscreen.LoginViewModel
import com.psfilter.feature_auth_module.presentation.onboarding.OnboardingViewModel
import com.psfilter.feature_auth_module.presentation.signupscreen.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { FirebaseAuth.getInstance() }
    single {
        GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.WEB_CLIENT_ID)
            .requestEmail()
            .requestProfile()
            .build()
    }
    single {
        GoogleSignIn.getClient(androidContext(), get<GoogleSignInOptions>())
    }
    single { FirebaseFirestore.getInstance() }
    single { CredentialManager.create(androidContext()) }
    single<EmailAuthRepository> { EmailAuthRepositoryImpl(get()) }
    single<GoogleAuthRepository> { GoogleAuthRepositoryImpl(androidContext(), get()) }
    single { EmailPasswordResetUseCase(get()) }
    single { EmailSignUpUseCase(get(), get()) }
    single { GmailLogInUseCase(get(),get(),get(), get()) }
    single { EmailLogOutUseCase(get()) }
    single { GmailLogOutUseCase(get()) }
    single { EmailLogInUseCase(get(),get(),get(), get()) }

    viewModel { OnboardingViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ForgetPasswordViewModel(get()) }
}