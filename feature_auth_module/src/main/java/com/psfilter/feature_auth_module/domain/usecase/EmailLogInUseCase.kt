package com.psfilter.feature_auth_module.domain.usecase

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.core_module.service.UserPreferenceManager
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.psfilter.feature_auth_module.data.model.Profile
import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository
import kotlinx.coroutines.tasks.await

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
                    createNewFirestoreAccount(authResult, userUid)
                    userPreferenceManager.setUserId(userUid)
                }
                result
            }
            else -> result
        }
    }

    private suspend fun createNewFirestoreAccount(authResult: AuthResult, userUid: String) {
        val user = authResult.user
        val profile = user?.let { createProfile(it) }

        if (profile != null) {
            try {
                firestore.collection(COLLECTION_USERS).document(userUid).set(profile).await()
            } catch (e: Exception) {
                // Handle potential errors, e.g., log them or display an error message
                e.printStackTrace()
            }
        } else {
            // Handle the case where profile creation failed or user is null
            // e.g., throw an exception or return an error result
            throw IllegalArgumentException("Profile creation failed or user is null")
        }
    }

    suspend fun auth(): RequestResult<Unit, DataError.Firebase> {
        return try {
            auth.currentUser?.let {
                RequestResult.Success(Unit)
            } ?: run {
                // Handle the case where the user is not authenticated
                RequestResult.Error(DataError.Firebase.UID_PROBLEM)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            RequestResult.Error(DataError.Firebase.LOGIN_PROBLEM)
        }
    }

    private fun createProfile(
        user: FirebaseUser
    ): Profile {
        return Profile(
            id = user.uid,
            email = user.email ?: throw IllegalStateException("Email is missing"),
            username = user.displayName
        )
    }
    companion object {
        private const val COLLECTION_USERS = "users"

    }
}