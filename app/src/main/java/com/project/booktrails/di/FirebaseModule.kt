package com.project.booktrails.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.dsl.module

val firebaseModule = module {

    single { Firebase.auth }
    single { FirebaseMessaging.getInstance() }

}