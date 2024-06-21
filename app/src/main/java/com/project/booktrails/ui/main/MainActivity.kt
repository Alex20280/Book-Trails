package com.project.booktrails.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.booktrails.feature_bestseller_books_module.bestsellerscreen.BestsellerScreen
import com.booktrails.feature_profile_module.ProfileScreen
import com.booktrails.feature_reading_module.bookdetailsscreen.HomeScreen
import com.project.booktrails.ui.navigation.BottomNavigationSection
import com.project.booktrails.ui.navigation.NavigationScreens
//import com.project.booktrails.navigation.NavigationScreens
import com.project.booktrails.ui.theme.BookTrailsTheme
import com.project.feature_auth_module.SplashScreen
import com.project.feature_auth_module.ui.ForgetPasswordScreen
import com.project.feature_auth_module.ui.LoginScreen
import com.project.feature_auth_module.ui.SignUpScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()
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
                    bottomBar = {
                     if (bottomBarVisible) {
                            BottomNavigationSection(
                                bottomNavigationScreens = bottomNavigationScreens,
                                navigateToHomeScreen = {navController.navigate(NavigationScreens.HomeScreen)},
                                navigateToBestsellerScreen = { navController.navigate(NavigationScreens.BestSellerScreen)},
                                navigateToProfileScreen = { navController.navigate(NavigationScreens.ProfileScreen)},
                                currentRoute = currentRoute
                            )
                       }
                    }) { innerPadding ->


                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreens.SplashScreen
                    ) {
                        composable<NavigationScreens.SplashScreen> {
                            SplashScreen(
                                paddingValues = innerPadding,
                                navigate = {
                                    navController.navigate(NavigationScreens.LoginScreen) {
                                        popUpTo(NavigationScreens.SplashScreen) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable<NavigationScreens.LoginScreen> {
                            LoginScreen(
                                paddingValues = innerPadding,
                                onClickForgetPassword = { navController.navigate(NavigationScreens.ForgetPasswordScreen) },
                                onRegisterClick = { navController.navigate(NavigationScreens.SignUpScreen) },
                                onSignInClick = { navController.navigate(NavigationScreens.HomeScreen) })
                        }

                      composable<NavigationScreens.HomeScreen> {
                            HomeScreen()
                        }

                        composable<NavigationScreens.BestSellerScreen> {
                            BestsellerScreen()
                        }

                        composable<NavigationScreens.ProfileScreen> {
                            ProfileScreen()
                        }


                        composable<NavigationScreens.SignUpScreen> {
                            val context = LocalContext.current
                            SignUpScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                                onRegisterButtonClick = {
                                    Toast.makeText(
                                        context,
                                        "click",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onTosClick = {
                                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
                                }, //TODO
                                onPrivacyClick = {
                                    Toast.makeText(
                                        context,
                                        "click",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }, //TODO
                            )
                        }

                        composable<NavigationScreens.ForgetPasswordScreen> {
                            ForgetPasswordScreen(
                                paddingValues = innerPadding,
                                onClickBackButton = { navController.navigateUp() },
                                onRestorePasswordClick = { }) //TODO
                        }

                        navController.addOnDestinationChangedListener { _, destination, _ ->
                            Log.d("MainAct", destination.route.toString())
                            Log.d("MainAct", NavigationScreens.HomeScreen::class.qualifiedName.toString())
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
                        }

                    }
                }

            }

        }

    }

}
