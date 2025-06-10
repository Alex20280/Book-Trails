package com.network_module.interceptor

import com.network_module.TokenManager
import com.network_module.api.BookTrailsTokenApi
import com.network_module.model.RefreshTokenResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(
    private val tokenManager: TokenManager,
    private val tokenApi: BookTrailsTokenApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getRefreshToken().first()
        }

        return runBlocking {
            val newToken = getNewToken(token)

            // Early return if refresh fails
            if (!newToken.isSuccessful || newToken.body() == null) {
                tokenManager.deleteToken()
                return@runBlocking null
            }

            // Extract the new access token
            val newAccessToken = newToken.body()!!.accessToken

            // Store only the new access token (refresh token remains unchanged)
            tokenManager.setToken(newAccessToken)

            // Build authenticated request
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<RefreshTokenResponse> {
        return tokenApi.refreshToken("Bearer $refreshToken")
    }
}