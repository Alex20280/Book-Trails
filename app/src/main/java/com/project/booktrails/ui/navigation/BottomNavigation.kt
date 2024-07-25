package com.project.booktrails.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.booktrails.R

@Composable
fun BottomNavigationSection(
    bottomNavigationScreens: List<NavigationScreens>,
    navigateToHomeScreen: () -> Unit,
    navigateToBestsellerScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    currentRoute: String?
) {

    val paddingValues = WindowInsets.navigationBars.asPaddingValues()
    val homeScreen = NavigationScreens.HomeScreen::class.qualifiedName.toString()
    val bestSellerScreen = NavigationScreens.BestSellerScreen::class.qualifiedName.toString()
    val profileScreen = NavigationScreens.ProfileScreen::class.qualifiedName.toString()

    BottomNavigation(
        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
    ) {

        BottomNavigationItem(
            modifier = Modifier
                .background(colorResource(id = com.booktrails.ui_module.R.color.white)),
            unselectedContentColor = colorResource(id = com.booktrails.ui_module.R.color.black),
            selectedContentColor = colorResource(id = com.booktrails.ui_module.R.color.blue),
            icon = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = bottomNavigationScreens[0].icon),
                        colorFilter = if (currentRoute == homeScreen) ColorFilter.tint(
                            colorResource(id = com.booktrails.ui_module.R.color.blue)
                        ) else null,
                        contentDescription = stringResource(R.string.home_page)
                    )
                    androidx.compose.material3.Text(
                        text = "Books",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (currentRoute == homeScreen) {
                            colorResource(id = com.booktrails.ui_module.R.color.blue)
                        } else {
                            colorResource(id = com.booktrails.ui_module.R.color.light_grey)
                        },
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                }
            },
            selected = currentRoute == homeScreen,
            onClick = {
                navigateToHomeScreen.invoke()
            }
        )

        BottomNavigationItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = com.booktrails.ui_module.R.color.white)),
            unselectedContentColor = colorResource(id = com.booktrails.ui_module.R.color.black),
            selectedContentColor = colorResource(id = com.booktrails.ui_module.R.color.blue),
            icon = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = bottomNavigationScreens[1].icon),
                        colorFilter = if (currentRoute == bestSellerScreen) ColorFilter.tint(
                            colorResource(id = com.booktrails.ui_module.R.color.blue)
                        ) else null,
                        contentDescription = stringResource(R.string.bestseller_screen)
                    )
                    androidx.compose.material3.Text(
                        text = "Bestseller",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (currentRoute == bestSellerScreen) {
                            colorResource(id = com.booktrails.ui_module.R.color.blue)
                        } else {
                            colorResource(id = com.booktrails.ui_module.R.color.light_grey)
                        },
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                }
            },
            selected = currentRoute == bestSellerScreen,
            onClick = {
                navigateToBestsellerScreen.invoke()
            }
        )

        BottomNavigationItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = com.booktrails.ui_module.R.color.white)),
            unselectedContentColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
            selectedContentColor = colorResource(id = com.booktrails.ui_module.R.color.blue),
            icon = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = bottomNavigationScreens[2].icon),
                        colorFilter = if (currentRoute == profileScreen) ColorFilter.tint(
                            colorResource(id = com.booktrails.ui_module.R.color.blue)
                        ) else null,
                        contentDescription = stringResource(R.string.profile_screen)
                    )
                    androidx.compose.material3.Text(
                        text = "Profile",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (currentRoute == profileScreen) {
                            colorResource(id = com.booktrails.ui_module.R.color.blue)
                        } else {
                            colorResource(id = com.booktrails.ui_module.R.color.light_grey)
                        },
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                }
            },
            selected = currentRoute == profileScreen,
            onClick = {
                navigateToProfileScreen.invoke()
            }
        )
    }
}





