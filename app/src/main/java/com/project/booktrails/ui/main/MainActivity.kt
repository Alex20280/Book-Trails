package com.project.booktrails.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.booktrails.ui.navigation.NavigationScreens
//import com.project.booktrails.navigation.NavigationScreens
import com.project.booktrails.ui.theme.BookTrailsTheme
import com.project.feature_auth_module.SplashScreen
import com.project.feature_auth_module.ui.ForgetPasswordScreen
import com.project.feature_auth_module.ui.LoginScreen
import com.project.feature_auth_module.ui.LoginScreenUI
import com.project.feature_auth_module.ui.SignUpScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        setContent {
            BookTrailsTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
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
                                onRegisterClick = { navController.navigate(NavigationScreens.SignUpScreen) })
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

                    }
                }


                /*                Scaffold(
                                    bottomBar =
                                )*/
                /*                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                   MainScreenView()
                                    Column {
                                        SplashScreen()
                                        LoginScreen()
                                        SignUpScreen()
                                        ForgetPasswordScreen()
                                    }


                                }*/

                //ArrangeNavigation()


            }
        }

    }


    /*    @Serializable
        object SplashScreen*/
}
