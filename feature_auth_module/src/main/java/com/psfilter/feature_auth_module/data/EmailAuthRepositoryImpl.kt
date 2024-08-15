package com.psfilter.feature_auth_module.data

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository
import kotlinx.coroutines.tasks.await

class EmailAuthRepositoryImpl(
    private val auth: FirebaseAuth
) : EmailAuthRepository() {

    override suspend fun registerUser(
        email: String,
        password: String
    ): RequestResult<Task<AuthResult>, DataError.Firebase> {
        return try {
            val authResultTask = auth.createUserWithEmailAndPassword(email, password)
            RequestResult.Success(authResultTask)
        } catch (e: Exception) {
            RequestResult.Error(DataError.Firebase.REGISTRATION_PROBLEM)
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): RequestResult<AuthResult, DataError.Firebase> {
        return try {
            val authResultTask = auth.signInWithEmailAndPassword(email, password)
            authResultTask.await()
            RequestResult.Success(authResultTask.result)
        } catch (e: Exception) {
            RequestResult.Error(DataError.Firebase.LOGIN_PROBLEM)
        }
    }

    override suspend fun resetPassword(email: String): RequestResult<Task<Void>, DataError.Firebase> {
        return try {
            val resetResultTask = auth.sendPasswordResetEmail(email)
            resetResultTask.await()
            RequestResult.Success(resetResultTask)
        } catch (e: Exception) {
            RequestResult.Error(DataError.Firebase.RESET_PROBLEM)
        }
    }

    override suspend fun logOut() {
       auth.signOut()
    }

    override suspend fun getAuthProviders(): List<String>? {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.providerData?.map { it.providerId }
    }

    override suspend fun getFirebaseAuth(): FirebaseUser? {
        return auth.currentUser
    }

}