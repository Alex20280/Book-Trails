package com.project.booktrails.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.booktrails.feature_bestseller_books_module.bestsellerdetails.BestsellerDetailsScreen
import com.booktrails.feature_bestseller_books_module.bestsellerscreen.BestsellerScreen
import com.booktrails.feature_book_management_module.addbookscreen.AddBookScreen
import com.booktrails.feature_book_management_module.manualaddscreen.ManualAddScreen
import com.booktrails.feature_book_management_module.scanisbnscreen.ScanIsbnScreen
import com.booktrails.feature_profile_module.contactdevscreen.ContactDeveloperScreen
import com.booktrails.feature_profile_module.profilescreen.ProfileScreen
import com.booktrails.feature_profile_module.settingsscreen.SettingsScreen
import com.booktrails.feature_reading_module.bookdetailsscreen.BookDetailsScreen
import com.booktrails.feature_reading_module.congratulationscreen.CongratulationScreen
import com.booktrails.feature_reading_module.homescreen.HomeScreen
import com.booktrails.feature_reading_module.readingtimesscreen.ReadingTimerScreen
import com.booktrails.ui_module.ToolBar
import com.project.booktrails.R
import com.project.booktrails.ui.navigation.BottomNavigationSection
import com.project.booktrails.ui.navigation.NavigationScreens
import com.project.booktrails.ui.theme.BookTrailsTheme
import com.project.feature_auth_module.ui.ForgetPasswordScreen
import com.project.feature_auth_module.ui.LoginScreen
import com.project.feature_auth_module.ui.SignUpScreen
import com.psfilter.feature_auth_module.presentation.onboarding.OnBoardingOneScreen
import com.psfilter.feature_auth_module.presentation.onboarding.OnBoardingThreeScreen
import com.psfilter.feature_auth_module.presentation.onboarding.OnBoardingTwoScreen
import com.psfilter.feature_auth_module.presentation.policy.PrivacyPolicyScreen
import com.psfilter.feature_auth_module.presentation.tos.TosScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity() : ComponentActivity() {

    private var keepSplashScreen = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreen
            holdSplashScreen()
            keepSplashScreen
        }

        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            var topBarVisible by remember { mutableStateOf(false) }
            var bottomBarVisible by remember { mutableStateOf(false) }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val bottomNavigationScreens = listOf(
                NavigationScreens.HomeScreen,
                NavigationScreens.BestSellerScreen,
                NavigationScreens.ProfileScreen,
            )

            BookTrailsTheme {

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (topBarVisible) {
                            ToolBar(stringResource(R.string.book_trails))
                        }
                    },
                    bottomBar = {
                        if (bottomBarVisible) {
                            BottomNavigationSection(
                                bottomNavigationScreens = bottomNavigationScreens,
                                navigateToHomeScreen = { navController.navigate(NavigationScreens.HomeScreen) },
                                navigateToBestsellerScreen = {
                                    navController.navigate(
                                        NavigationScreens.BestSellerScreen
                                    )
                                },
                                navigateToProfileScreen = { navController.navigate(NavigationScreens.ProfileScreen) },
                                currentRoute = currentRoute
                            )
                        }
                    }) { innerPadding ->


                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreens.OnBoardingOneScreen //NavigationScreens.LoginScreen
                    ) {
                        composable<NavigationScreens.LoginScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ) {
                            LoginScreen(
                                paddingValues = innerPadding,
                                onClickForgetPassword = { navController.navigate(NavigationScreens.ForgetPasswordScreen) },
                                onRegisterClick = { navController.navigate(NavigationScreens.SignUpScreen) },
                                onSignInClick = { navController.navigate(NavigationScreens.HomeScreen){
                                    popUpTo(NavigationScreens.LoginScreen) { inclusive = true }
                                } })
                        }
                        composable<NavigationScreens.TosScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            TosScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                            )
                        }
                        composable<NavigationScreens.OnBoardingOneScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            OnBoardingOneScreen(
                                paddingValues = innerPadding,
                                onNextClick = {navController.navigate(NavigationScreens.OnBoardingTwoScreen){
                                    popUpTo(NavigationScreens.OnBoardingOneScreen) { inclusive = true }
                                } },
                                navigateToLoginScreen = {navController.navigate(NavigationScreens.LoginScreen){
                                    popUpTo(NavigationScreens.OnBoardingOneScreen) { inclusive = true }
                                } },
                                navigateToHomeScreen = {navController.navigate(NavigationScreens.HomeScreen){
                                    popUpTo(NavigationScreens.OnBoardingOneScreen) { inclusive = true }
                                } }
                            )
                        }
                        composable<NavigationScreens.OnBoardingTwoScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            OnBoardingTwoScreen(
                                paddingValues = innerPadding,
                                onNextClick = {navController.navigate(NavigationScreens.OnBoardingThreeScreen){
                                    popUpTo(NavigationScreens.OnBoardingTwoScreen) { inclusive = true }
                                } },
                            )
                        }
                        composable<NavigationScreens.OnBoardingThreeScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            OnBoardingThreeScreen(
                                paddingValues = innerPadding,
                                onNextClick = {navController.navigate(NavigationScreens.LoginScreen){
                                    popUpTo(NavigationScreens.OnBoardingThreeScreen) { inclusive = true }
                                } },

                            )
                        }
                        composable<NavigationScreens.PrivacyPolicyScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            PrivacyPolicyScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                            )
                        }
                        composable<NavigationScreens.HomeScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ) {
                            HomeScreen(
                                navigateToDetailsScreen = {navController.navigate(NavigationScreens.BookDetailsScreen(it))},
                                navigateToAddBookScreen = {navController.navigate(NavigationScreens.AddBookScreen)}
                            )
                        }

                        composable<NavigationScreens.AddBookScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            AddBookScreen(
                                navigateToIsbnScanScreen = {navController.navigate(NavigationScreens.ScanIsbnScreen)},
                                navigateToManualAddScreen = {navController.navigate(NavigationScreens.ManualAddScreen)}
                            )
                        }
                        composable<NavigationScreens.ScanIsbnScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            ScanIsbnScreen(navigateToManualScreen = {navController.navigate(NavigationScreens.ManualAddScreen)})
                        }

                        composable<NavigationScreens.ManualAddScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            ManualAddScreen(navigateToHomeScreen = {navController.navigate(NavigationScreens.HomeScreen){
                                popUpTo(NavigationScreens.ManualAddScreen) { inclusive = true }
                            }})
                        }

                        composable<NavigationScreens.BestSellerScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ) {
                            BestsellerScreen(
                                navigateToBestsellerDetailScreen = {
                                    navController.navigate(
                                        NavigationScreens.BestsellerDetailsScreen(it)
                                    )
                                }
                            )
                        }

                        composable<NavigationScreens.BestsellerDetailsScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            val bestsellerIdArgs = it.toRoute<NavigationScreens.BestsellerDetailsScreen>()
                            BestsellerDetailsScreen(bestsellerIdArgs = bestsellerIdArgs.id)
                        }

                        composable<NavigationScreens.ProfileScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            ProfileScreen(navigateToLogInScreen = {navController.navigate(NavigationScreens.LoginScreen){
                                popUpTo(NavigationScreens.ProfileScreen) { inclusive = true }
                            }},
                                navigateToSettingScreen = {navController.navigate(NavigationScreens.SettingsScreen)},
                                navigateToContactDevScreen = {navController.navigate(NavigationScreens.ContactDeveloperScreen)}
                            )
                        }
                        composable<NavigationScreens.SettingsScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            SettingsScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                            )
                        }
                        composable<NavigationScreens.ContactDeveloperScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            ContactDeveloperScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                            )
                        }

                        composable<NavigationScreens.BookDetailsScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            val idArgs = it.toRoute<NavigationScreens.BookDetailsScreen>()
                            BookDetailsScreen(idArgs = idArgs.id,
                                navigateToReadingTimerScreen = {navController.navigate(NavigationScreens.ReadingTimerScreen(it))}
                            )
                        }

                        composable<NavigationScreens.ReadingTimerScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            val idArgs = it.toRoute<NavigationScreens.ReadingTimerScreen>()
                            ReadingTimerScreen(idArgs = idArgs.id, navigateToCongratulationScreen = {
                                navController.navigate(NavigationScreens.CongratulationScreen)
                            })
                        }

                        composable<NavigationScreens.CongratulationScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            CongratulationScreen(navigateToHomeScreen = {navController.navigate(NavigationScreens.HomeScreen){
                                popUpTo(NavigationScreens.CongratulationScreen) { inclusive = true }
                            } })
                        }

                        composable<NavigationScreens.SignUpScreen>  (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            SignUpScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                                onRegisterButtonClick = { navController.navigateUp() },
                                onTosClick = {navController.navigate(NavigationScreens.TosScreen)},
                                onPrivacyClick = {navController.navigate(NavigationScreens.PrivacyPolicyScreen)},
                            )
                        }

                        composable<NavigationScreens.ForgetPasswordScreen> (
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                        ){
                            ForgetPasswordScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                                onRestorePasswordClick = { navController.navigateUp()})
                        }

                        navController.addOnDestinationChangedListener { _, destination, _ ->
                            bottomBarVisible = when (destination.route) {
                                NavigationScreens.HomeScreen::class.qualifiedName.toString(),
                                NavigationScreens.BestSellerScreen::class.qualifiedName.toString(),
                                NavigationScreens.ProfileScreen::class.qualifiedName.toString() -> {
                                    true
                                }

                                else -> {
                                    false
                                }
                            }

                            topBarVisible = when (destination.route) {
                                NavigationScreens.HomeScreen::class.qualifiedName.toString(),
                                NavigationScreens.BestSellerScreen::class.qualifiedName.toString(),
                                NavigationScreens.ProfileScreen::class.qualifiedName.toString() -> {
                                    true
                                }

                                else -> {
                                    false
                                }
                            }
                        }

                    }
                }

            }

        }

    }
    private fun holdSplashScreen(){
        lifecycleScope.launch {
            delay(1000)
            keepSplashScreen = false
        }
    }

}
