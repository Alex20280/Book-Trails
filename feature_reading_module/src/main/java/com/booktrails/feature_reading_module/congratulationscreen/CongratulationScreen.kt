package com.booktrails.feature_reading_module.congratulationscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.booktrails.ui_module.R
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CongratulationScreen(
    navigateToHomeScreen:() -> Unit
) {

    LaunchedEffect(key1 = true) {
        delay(11000)
        navigateToHomeScreen.invoke()
    }

    CongratulationScreenUI(
    )
}

@Composable
fun CongratulationScreenUI(
) {

    val compositionCongrats by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.anim)
    )
    val compositionConfetti by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.confetti_second_anim)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White), // Background color for better visualization
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f) // Half of the available height
                .background(color = Color.White) // Background color for upper half
        ) {
            LottieAnimation(
                composition = compositionCongrats,
                iterations = 3,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(color = Color.White)
        ) {
            LottieAnimation(
                composition = compositionConfetti,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}