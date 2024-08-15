package com.psfilter.feature_auth_module.domain.repository

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

abstract class GoogleAuthRepository {

    abstract fun loginWithGoogle(): Intent

    abstract suspend fun logOutWithGoogle()

    abstract suspend fun getCurrentGmailUser(): GoogleSignInAccount?

}