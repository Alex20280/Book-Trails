package com.psfilter.feature_auth_module.ui.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.TopBarBackground
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingOneScreen(
    onNextClick: () -> Unit,
    navigateToLoginScreen: () -> Unit
) {
    val viewModel: OnboardingViewModel = koinViewModel()
    val hasSeenOnboarding by viewModel.hasSeenOnboarding.collectAsState()

    LaunchedEffect(hasSeenOnboarding) {
        if (hasSeenOnboarding == true) {
            navigateToLoginScreen.invoke()
        }
    }

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

