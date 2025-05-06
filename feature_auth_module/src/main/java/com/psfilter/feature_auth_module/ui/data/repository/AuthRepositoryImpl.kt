package com.psfilter.feature_auth_module.ui.data.repository

import com.network_module.api.AuthApi
import com.psfilter.feature_auth_module.ui.domain.AuthRepository

class AuthRepositoryImpl(
    private val registerApi: AuthApi,
): AuthRepository {


}