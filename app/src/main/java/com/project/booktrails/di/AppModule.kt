package com.project.booktrails.di

import com.psfilter.feature_auth_module.ui.data.repository.AuthRepositoryImpl
import com.psfilter.feature_auth_module.ui.domain.AuthRepository
import com.psfilter.feature_auth_module.ui.domain.usecase.GetNotificationUseCase
import org.koin.dsl.module

val appModule = module {

    // RepositoryImpl
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    // UseCase
    factory { GetNotificationUseCase(authRepository = get()) }
}