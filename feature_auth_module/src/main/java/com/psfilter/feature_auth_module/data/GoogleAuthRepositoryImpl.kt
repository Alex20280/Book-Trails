package com.psfilter.feature_auth_module.data

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.psfilter.feature_auth_module.domain.repository.GoogleAuthRepository
import kotlinx.coroutines.tasks.await

class GoogleAuthRepositoryImpl(
    private val signInClient: GoogleSignInClient
): GoogleAuthRepository() {

    override fun loginWithGoogle(): Intent {
        return signInClient.signInIntent
    }

    override suspend fun logOutWithGoogle() {
        signInClient.revokeAccess().await()
    }
}