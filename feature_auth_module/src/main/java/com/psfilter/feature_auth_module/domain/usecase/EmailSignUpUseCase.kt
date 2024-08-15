package com.psfilter.feature_auth_module.domain.usecase

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.psfilter.feature_auth_module.data.model.Profile
import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository
import kotlinx.coroutines.tasks.await
import java.util.Date

class EmailSignUpUseCase(
    private val firestore:FirebaseFirestore,
    private val emailAuthRepository: EmailAuthRepository
) {
    suspend fun registerUser(email: String, password: String): RequestResult<Task<AuthResult>, DataError.Firebase> {
       return  emailAuthRepository.registerUser(email, password)
    }

    suspend fun createFirestoreAccount(task: Task<AuthResult>){
        val user = task.result.user
        val uid = task.result.user?.uid
        val profile = user?.let { createProfile(it) }

        if (profile != null) {
            try {
                firestore.collection(COLLECTION_USERS).document(uid.toString()).set(profile).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            throw IllegalArgumentException("Profile creation failed or user is null")
        }
    }

    private fun createProfile(
        user: FirebaseUser
    ): Profile {
        return Profile(
            id = user.uid,
            email = user.email ?: throw IllegalStateException("Email is missing"),
            displayName = user.displayName,
            registeredSince = Date()
        )
    }
    companion object {
        private const val COLLECTION_USERS = "users"

    }

}