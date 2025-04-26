package com.psfilter.feature_auth_module.ui.data.repository

import android.net.wifi.WifiConfiguration.AuthAlgorithm
import com.network_module.api.AuthApi
import com.network_module.api.PushNotificationRequest
import com.psfilter.feature_auth_module.ui.domain.AuthRepository

class AuthRepositoryImpl(
    private val registerApi: AuthApi,
): AuthRepository {

    override suspend fun testNotification() {
        registerApi.testNotification(PushNotificationRequest("","", "fEHSL28iQ_eSWqiLrAQ3CP:APA91bEsHV3t5y4dEBrHd1qK7VLOJBL73AseyzY1Hqolt30W9t3D5xZOZw7WywKXKY4exU0IVmM1lUfIckGdmpc741lnDaJvCDheIvNj0gWttHr1kVUq86M"))
    }
}