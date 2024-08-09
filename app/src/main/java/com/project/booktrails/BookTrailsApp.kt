package com.project.booktrails

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.booktrails.di.authModule
import com.project.booktrails.di.coreModule
import com.project.booktrails.di.profileModule
import com.project.booktrails.di.readingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookTrailsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        FirebaseAnalytics.getInstance(this)

        startKoin {
            androidContext(this@BookTrailsApp)
            modules(listOf(authModule, coreModule, readingModule, profileModule))
        }
    }
}
