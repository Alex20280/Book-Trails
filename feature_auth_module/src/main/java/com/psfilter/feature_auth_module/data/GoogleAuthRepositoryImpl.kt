package com.psfilter.feature_auth_module.data

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.psfilter.feature_auth_module.domain.repository.GoogleAuthRepository
import kotlinx.coroutines.tasks.await

class GoogleAuthRepositoryImpl(
    private val context: Context,
    private val signInClient: GoogleSignInClient
): GoogleAuthRepository() {

    override fun loginWithGoogle(): Intent {
        return signInClient.signInIntent
    }

    override suspend fun logOutWithGoogle() {
        signInClient.revokeAccess().await()
    }

    override suspend fun getCurrentGmailUser(): GoogleSignInAccount?  {
        return GoogleSignIn.getLastSignedInAccount(context)
    }
}