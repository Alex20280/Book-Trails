package com.project.booktrails.ui.navigation

import kotlinx.serialization.Serializable

sealed class NavigationScreens {
    @Serializable
    data object SplashScreen: NavigationScreens()
    @Serializable
    data object SignUpScreen: NavigationScreens()
    @Serializable
    data object LoginScreen: NavigationScreens()
    @Serializable
    data object ForgetPasswordScreen: NavigationScreens()
}