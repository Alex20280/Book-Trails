package com.booktrails.core_module.errorhandling

typealias RootError = Error

sealed interface RequestResult<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): RequestResult<D, E>
    data class Error<out D, out E: RootError>(val error: E): RequestResult<D, E>
    object Loading: RequestResult<Nothing, Nothing>
}