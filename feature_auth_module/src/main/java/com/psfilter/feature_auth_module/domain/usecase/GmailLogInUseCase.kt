package com.psfilter.feature_auth_module.domain.usecase

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.core_module.service.UserPreferenceManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.psfilter.feature_auth_module.data.model.Profile
import com.psfilter.feature_auth_module.data.model.SignInModel
import com.psfilter.feature_auth_module.domain.repository.GoogleAuthRepository
import kotlinx.coroutines.tasks.await

class GmailLogInUseCase(
    private val auth: FirebaseAuth,
    private val userPreferenceManager: UserPreferenceManager,
    private val firestore: FirebaseFirestore,
    private val googleAuthRepository: GoogleAuthRepository
) {

    fun logInWithGmail(): Intent {
        return googleAuthRepository.loginWithGoogle()
    }

    suspend fun signIn(result: ActivityResult?): RequestResult<Unit, DataError.Firebase> {
        return try {
            if (result == null || result.resultCode != Activity.RESULT_OK) {
                throw IllegalStateException("Google Sign-In activity result is null or not successful")
            }
            authFirebaseWithGoogle(result.data)
            RequestResult.Success(Unit)
        } catch (e: FirebaseAuthException) {
            RequestResult.Error(DataError.Firebase.FIREBASE_AUTH_EXCEPTION)
        } catch (e: Exception) {
            RequestResult.Error(DataError.Firebase.UNEXPECTED_ERROR)
        }
    }

    @Throws(ApiException::class)
    private suspend fun authFirebaseWithGoogle(intent: Intent?)  {
        val result = GoogleSignIn.getSignedInAccountFromIntent(intent).result

        val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
        if (isAnyLinkedAccountExists(result?.email ?: throw IllegalStateException("Google account 'email' is missing"))) {
            val uid = auth.signInWithCredential(credentials).await().user?.uid
            userPreferenceManager.setUserId(uid ?: throw IllegalStateException("User id is null"))
        }
        else {
            val uid = auth.currentUser?.uid ?: throw IllegalStateException("User is not logged in")
            linkWithCurrentUser(credentials)
            createNewFirebaseAccount(result, uid)
        }
    }

    private suspend fun createNewFirebaseAccount(googleSignInAccount: GoogleSignInAccount, uid: String) {
        val profile = createProfileWithGoogle(googleSignInAccount, uid)
        firestore.collection(COLLECTION_USERS).document(uid).set(profile).await()
        userPreferenceManager.setUserId(profile.id)
    }

    private fun createProfileWithGoogle(
        googleSignInAccount: GoogleSignInAccount,
        userId: String
    ): Profile {
        return Profile(
            id = userId,
            email = googleSignInAccount.email ?: throw IllegalStateException("Google account 'email' is missing"),
            photoUrl = googleSignInAccount.photoUrl.toString(),
            signInModel = SignInModel.GOOGLE.toString(),
            username = googleSignInAccount.displayName ?: throw IllegalStateException("Google account 'name' is missing")
        )
    }

    private suspend fun linkWithCurrentUser(credential: AuthCredential) {
        val result = auth.currentUser?.linkWithCredential(credential)?.await()
        if (result?.user == null || result.credential == null) throw IllegalStateException("Credential linking failed")
    }

    private suspend fun isAnyLinkedAccountExists(email: String): Boolean {
        return !firestore.collection(COLLECTION_USERS).whereEqualTo(FIELD_EMAIL, email).get()
            .await().isEmpty
    }

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val FIELD_EMAIL = "email"
    }

}