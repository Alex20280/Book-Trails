package com.psfilter.feature_auth_module.ui.presentation.onboardingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton

@Composable
fun OnBoardingOneScreen(
    onNextClick: () -> Unit,
    navigateToLoginScreen: () -> Unit
) {
/*    val viewModel: SplashScreenViewModel = koinViewModel()
    val hasSeenOnboarding by viewModel.isFirstTimeAppRun.collectAsState()

    LaunchedEffect(hasSeenOnboarding) {
        if (hasSeenOnboarding == true) {
            navigateToLoginScreen.invoke()
        }
    }*/

    OnBoardingOneScreenUI(
        onNextClick = onNextClick
    )
}

@Composable
fun OnBoardingOneScreenUI(
    onNextClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_cream))
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(com.project.feature_auth_module.R.string.make_a_habit_to_read_everyday_and_get_rewards),
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            style = MaterialTheme.typography.headlineLarge,
            color = colorResource(id = R.color.dark_brown),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 16.dp)
        )


        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = com.project.feature_auth_module.R.drawable.onboarding_one),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubmitButton(text = "Next", onClick = onNextClick)
    }

}

