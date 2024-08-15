package com.psfilter.feature_auth_module.domain.usecase

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.core_module.service.UserPreferenceManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.psfilter.feature_auth_module.data.model.Profile
import com.psfilter.feature_auth_module.data.model.SignInModel
import com.psfilter.feature_auth_module.data.model.UserState
import com.psfilter.feature_auth_module.domain.repository.GoogleAuthRepository
import kotlinx.coroutines.tasks.await
import java.util.Date

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
            Log.d("MyError", "result and error: $result + $e")
            RequestResult.Error(DataError.Firebase.UNEXPECTED_ERROR)
        }
    }

    private suspend fun authFirebaseWithGoogle(intent: Intent?) {
        val result = GoogleSignIn.getSignedInAccountFromIntent(intent).result
        val credentials = GoogleAuthProvider.getCredential(result.idToken, null)

        if (isAnyLinkedAccountExists(
                result?.email ?: throw IllegalStateException("Google account 'email' is missing")
            )
        ) {
            val uid = auth.signInWithCredential(credentials).await().user?.uid
            userPreferenceManager.setUserId(uid ?: throw IllegalStateException("User id is null"))
            updateCurrentUser(uid, result)

        } else {
            val uid = auth.signInWithCredential(credentials).await().user?.uid
                ?: throw IllegalStateException("User is not logged in")
            createNewFirebaseAccount(result, uid)
        }
    }

    private fun updateCurrentUser(uid: String, googleSignInAccount: GoogleSignInAccount) {
        val name = googleSignInAccount.displayName
        val photo = googleSignInAccount.photoUrl
        firestore.collection(COLLECTION_USERS).document(uid).update(
            "displayName", name,
            "photoUrl", photo,
            "signInModel", SignInModel.GMAIL.toString(),
            "lastLogIn", Date()
        )
    }

    private suspend fun createNewFirebaseAccount(
        googleSignInAccount: GoogleSignInAccount,
        uid: String
    ) {
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
            email = googleSignInAccount.email
                ?: throw IllegalStateException("Google account 'email' is missing"),
            photoUrl = googleSignInAccount.photoUrl.toString(),
            lastLogIn = Date(),
            registeredSince = Date(),
            signInModel = SignInModel.GMAIL.toString(),
            displayName = googleSignInAccount.displayName
                ?: throw IllegalStateException("Google account 'name' is missing")
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