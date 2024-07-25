package com.project.booktrails.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationScreens(val icon: Int, val route: String) {
    @Serializable
    data object SignUpScreen: NavigationScreens(0, "")
    @Serializable
    data object TosScreen: NavigationScreens(0, "")
    @Serializable
    data object OnBoardingOneScreen: NavigationScreens(0, "")
    @Serializable
    data object OnBoardingTwoScreen: NavigationScreens(0, "")
    @Serializable
    data object OnBoardingThreeScreen: NavigationScreens(0, "")
    @Serializable
    data object PrivacyPolicyScreen: NavigationScreens(0, "")
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
    @Serializable
    data object SettingsScreen: NavigationScreens(0, "")
    @Serializable
    data object ContactDeveloperScreen: NavigationScreens(0, "")
    @Serializable
    data class BookDetailsScreen(val id: Int): NavigationScreens(0, "bookdetails/{id}")
    @Serializable
    data class ReadingTimerScreen(val id: Int): NavigationScreens(0, "readingtimer/{id}")
    @Serializable
    data object CongratulationScreen: NavigationScreens(0, "")
    @Serializable
    data class BestsellerDetailsScreen(val id: Int): NavigationScreens(0, "bestsellerdetails/{id}")
    @Serializable
    data object AddBookScreen: NavigationScreens(0, "")
    @Serializable
    data object ScanIsbnScreen: NavigationScreens(0, "")
    @Serializable
    data object ManualAddScreen: NavigationScreens(0, "")
}