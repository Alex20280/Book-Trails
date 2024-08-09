package com.project.booktrails.di

import com.booktrails.feature_reading_module.homescreen.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val readingModule = module {

    viewModel { HomeScreenViewModel() }
}