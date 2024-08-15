package com.psfilter.feature_auth_module.domain.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.psfilter.feature_auth_module.domain.repository.GoogleAuthRepository

class GmailLogOutUseCase(
    private val googleAuthRepository: GoogleAuthRepository
) {

    suspend fun logOutWithGoogle(){
        googleAuthRepository.logOutWithGoogle()
    }

}