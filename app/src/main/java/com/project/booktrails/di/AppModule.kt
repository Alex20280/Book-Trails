package com.project.booktrails.di

import com.psfilter.feature_auth_module.ui.data.repository.AuthRepositoryImpl
import com.psfilter.feature_auth_module.ui.domain.AuthRepository
import com.psfilter.feature_auth_module.ui.domain.usecase.LoginWithEmailPasswordUseCase
import com.psfilter.feature_auth_module.ui.domain.usecase.RegisterWithEmailAndPasswordUseCase
import com.psfilter.feature_auth_module.ui.domain.usecase.ResendEmailVerificationCodeUseCase
import com.psfilter.feature_auth_module.ui.domain.usecase.VerifyEmailUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {

    single<CoroutineDispatcher> { Dispatchers.IO }

    // RepositoryImpl
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    // UseCase
    single { RegisterWithEmailAndPasswordUseCase(get()) }
    single { VerifyEmailUseCase(get()) }
    single { ResendEmailVerificationCodeUseCase(get()) }
    single { LoginWithEmailPasswordUseCase(get()) }
}