package com.project.booktrails.di

import com.booktrails.feature_profile_module.profilescreen.ProfileScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel { ProfileScreenViewModel(get(), get()) }
}