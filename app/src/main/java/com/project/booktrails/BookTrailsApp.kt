package com.project.booktrails

import android.app.Application
import com.project.booktrails.di.appModule
import com.project.booktrails.di.dataStoreModule
import com.project.booktrails.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class BookTrailsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@BookTrailsApp)
            modules(listOf(appModule, viewModelModule, dataStoreModule))
        }
    }
}