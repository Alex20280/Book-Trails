package com.psfilter.feature_auth_module.domain.usecase

import android.util.Log
import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.core_module.service.UserPreferenceManager
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.psfilter.feature_auth_module.data.model.Profile
import com.psfilter.feature_auth_module.data.model.SignInModel
import com.psfilter.feature_auth_module.data.model.UserState
import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository
import kotlinx.coroutines.tasks.await
import java.util.Date

class EmailLogInUseCase(
    private val auth: FirebaseAuth,
    private val emailAuthRepository: EmailAuthRepository,
    private val firestore: FirebaseFirestore,
    private val userPreferenceManager: UserPreferenceManager
) {

    suspend fun loginEmailPassword(
        email: String,
        password: String
    ): RequestResult<AuthResult, DataError.Firebase> {
        return when (val result = emailAuthRepository.loginUser(email, password)) {
            is RequestResult.Success -> {
                val authResult = result.data
                val userUid = authResult.user?.uid
                if (userUid != null) {
                    updateFirestoreAccount(authResult, userUid)
                    userPreferenceManager.setUserId(userUid)
                }
                result
            }
            else -> result
        }
    }

    private suspend fun updateFirestoreAccount(authResult: AuthResult, userUid: String) {
        val user = authResult.user
        val uid = authResult.user?.uid
        val document = firestore.collection("users").document(uid.toString()).get().await()
        val profile = user?.let { updateProfile(it, document) }

        if (profile != null) {
            try {
                firestore.collection(COLLECTION_USERS).document(userUid).set(profile).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            throw IllegalArgumentException("Profile creation failed or user is null")
        }
    }

    suspend fun auth(): RequestResult<Unit, DataError.Firebase> {
        Log.d("MYactiveids", "login: ${auth.currentUser?.uid}")
        return try {
            auth.currentUser?.let {
                RequestResult.Success(Unit)
            } ?: run {
                RequestResult.Error(DataError.Firebase.UID_PROBLEM)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            RequestResult.Error(DataError.Firebase.LOGIN_PROBLEM)
        }
    }

    private fun updateProfile(
        user: FirebaseUser,
        document: DocumentSnapshot
    ): Profile {

        val currentRemoteProfile = getCurrentFirestoreAccount(user.uid, document)
        return Profile(
            id = user.uid,
            email = user.email ?: throw IllegalStateException("Email is missing"),
            photoUrl = currentRemoteProfile?.photoUrl ?: throw IllegalStateException("Photo is missing"),
            displayName = currentRemoteProfile.displayName,
            registeredSince = currentRemoteProfile.registeredSince,
            userState = currentRemoteProfile.userState,
            signInModel = currentRemoteProfile.signInModel,
            lastLogIn = Date()
        )
    }

    private fun getCurrentFirestoreAccount(uid: String, document: DocumentSnapshot): Profile? {
        return if (document.exists()) {
            val data = document.data ?: return null
            Profile(
                id = uid,
                email = data["email"] as? String ?: "",
                photoUrl = data["photoUrl"] as? String ?: "",
                displayName = data["displayName"] as? String,
                registeredSince = (data["registeredSince"] as? com.google.firebase.Timestamp)?.toDate(),
                userState = data["userState"] as? String ?: UserState.NONE.value,
                signInModel = data["signInModel"] as? String ?: SignInModel.EMAIL.value
            )
        }else {
            null
        }
    }
    companion object {
        private const val COLLECTION_USERS = "users"

    }

}