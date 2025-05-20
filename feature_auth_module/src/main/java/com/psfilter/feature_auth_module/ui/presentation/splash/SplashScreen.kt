package com.psfilter.feature_auth_module.ui.presentation.splash

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

@Composable
fun SplashScreen(
    paddingValues: PaddingValues,
    onAnimationFinished: () -> Unit
) {
    var currentImageIndex by remember { mutableIntStateOf(0) }

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
        onAnimationFinished()
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