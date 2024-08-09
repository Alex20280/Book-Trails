package com.psfilter.feature_auth_module.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.TopBarBackground

@Composable
fun OnBoardingTwoScreen(
    paddingValues: PaddingValues,
    onNextClick: () -> Unit
) {

    OnBoardingTwoScreenUI(
        paddingValues = paddingValues,
        onNextClick = {onNextClick.invoke()}
    )
}

@Composable
fun OnBoardingTwoScreenUI(
    paddingValues: PaddingValues,
    onNextClick: () -> Unit
) {
    TopBarBackground()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding()
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.onboardingtwo_screen),
                style = MaterialTheme.typography.headlineLarge,
                color = colorResource(id = R.color.black),
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            SubmitButton(text = "Next", onClick = onNextClick, enabled = true) // TODO
        }
    }

}