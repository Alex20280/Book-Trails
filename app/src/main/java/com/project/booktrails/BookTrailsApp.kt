package com.project.booktrails

import android.app.Application
import com.booktrails.core_network_module.domain.di.networkModule
import com.google.firebase.FirebaseApp
import com.project.booktrails.di.authModule
import com.project.booktrails.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookTrailsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@BookTrailsApp)
            modules(listOf(authModule, coreModule))
        }
    }
}
