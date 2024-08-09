package com.project.booktrails.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.booktrails.core_module.service.UserPreferenceManager
import org.koin.dsl.module

val coreModule = module {
    single { UserPreferenceManager(get()) }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile("user_settings") }
        )
    }
}