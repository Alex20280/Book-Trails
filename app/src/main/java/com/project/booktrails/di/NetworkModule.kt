package com.project.booktrails.di

import com.network_module.TokenManager
import com.network_module.api.AuthApi
import com.network_module.api.BookTrailsApi
import com.network_module.api.BookTrailsTokenApi
import com.network_module.interceptor.AuthAuthenticator
import com.network_module.utils.NetworkUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = NetworkUtils.BASE_URL

val networkModule = module {

    single(qualifier = named("clearOkHttpClient")) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Retrofit without token
    single(qualifier = named("cleanRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(qualifier = named("clearOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<BookTrailsTokenApi> {
        get<Retrofit>(named("authorizationRetrofit")).create(BookTrailsTokenApi::class.java)
    }

    single<AuthApi> {
        get<Retrofit>(named("cleanRetrofit")).create(AuthApi::class.java)
    }

    single(qualifier = named("authorizationOkHttpClient")) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .authenticator(get<AuthAuthenticator>())
            .build()
    }

    // Retrofit with token
    single(qualifier = named("authorizationRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(qualifier = named("authorizationOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { TokenManager(get()) }

    single { AuthAuthenticator(get(), get()) }

    single<BookTrailsApi> {
        get<Retrofit>(named("authorizationRetrofit")).create(BookTrailsApi::class.java)
    }

}