package com.project.booktrails

import android.app.Application
import com.project.booktrails.di.appModule
/*import com.project.core_navigation_module.di.navigationModule
import com.project.feature_auth_module.di.featureAuthModule*/
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class BookTrailsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@BookTrailsApp)
            //modules(listOf(appModule, navigationModule, featureAuthModule))
            modules(listOf(appModule))
        }
    }
}