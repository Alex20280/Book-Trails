package com.psfilter.feature_auth_module.domain.repository

import android.content.Intent

abstract class GoogleAuthRepository {

    abstract fun loginWithGoogle(): Intent

    abstract suspend fun logOutWithGoogle()

}