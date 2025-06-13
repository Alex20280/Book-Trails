package com.psfilter.feature_auth_module.ui.presentation.splashscreen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.project.feature_auth_module.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import com.psfilter.feature_auth_module.ui.presentation.splashscreen.viewmodel.SplashScreenViewModel

@Composable
fun SplashScreen(
    paddingValues: PaddingValues,
    navigateToOnBoardingScreen: () -> Unit,
    navigateToLoginScreen: () -> Unit,
) {
    var currentImageIndex by remember { mutableIntStateOf(0) }

    val viewModel: SplashScreenViewModel = koinViewModel()
    val isFirstTimeAppRun by viewModel.isFirstTimeAppRun.collectAsState()

    val images = listOf(
        R.drawable.splash_one,
        R.drawable.splash_two,
        R.drawable.splash_three
    )

    LaunchedEffect(Unit) {
        repeat(images.size) { index ->
            currentImageIndex = index
            delay(1000L)
        }
        if (isFirstTimeAppRun == true) {
            navigateToLoginScreen.invoke()
        } else {
            navigateToOnBoardingScreen.invoke()
        }
    }

    SplashScreenScreenUI(
        paddingValues = paddingValues,
        currentImageIndex = currentImageIndex,
        images = images
    )
}

@Composable
fun SplashScreenScreenUI(
    paddingValues: PaddingValues,
    currentImageIndex: Int,
    images: List<Int>
) {
    Crossfade(
        targetState = currentImageIndex,
        animationSpec = tween(500),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) { index ->
        Image(
            painter = painterResource(id = images[index]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}