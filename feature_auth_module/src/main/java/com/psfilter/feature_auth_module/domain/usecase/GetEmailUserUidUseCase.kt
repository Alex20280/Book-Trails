package com.psfilter.feature_auth_module.domain.usecase

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult
import com.psfilter.feature_auth_module.domain.repository.EmailAuthRepository

class GetEmailUserUidUseCase(
    private val emailAuthRepository: EmailAuthRepository
) {

    suspend fun getUserUid(): RequestResult<String?, DataError> {
        return emailAuthRepository.getUserUd()
    }
}