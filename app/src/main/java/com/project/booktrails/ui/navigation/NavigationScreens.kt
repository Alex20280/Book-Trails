package com.project.booktrails.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationScreens(val icon: Int, val route: String) {
    @Serializable
    data object SplashScreen: NavigationScreens(0, "")
    @Serializable
    data object SignUpScreen: NavigationScreens(0, "")
    @Serializable
    data object LoginScreen: NavigationScreens(0, "")
    @Serializable
    data object ForgetPasswordScreen: NavigationScreens(0, "")
    @Serializable
    data object HomeScreen: NavigationScreens(com.project.booktrails.R.drawable.book_icon, "home")
    @Serializable
    data object BestSellerScreen: NavigationScreens( com.project.booktrails.R.drawable.star_icon, "bestseller")
    @Serializable
    data object ProfileScreen: NavigationScreens(com.project.booktrails.R.drawable.profile_icon, "profile")

}