package com.psfilter.feature_auth_module.ui.presentation.onboardingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.psfilter.feature_auth_module.ui.presentation.splashscreen.SplashScreenViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun OnBoardingThreeScreen(
    onNextClick: () -> Unit,
) {

    val viewModel: SplashScreenViewModel = koinViewModel()

    OnBoardingThreeScreenUI(
        onNextClick = {
            viewModel.setOnboardingSeen()
            onNextClick.invoke()
        }
    )
}

@Composable
fun OnBoardingThreeScreenUI(
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
            text = stringResource(com.project.feature_auth_module.R.string.add_your_books_easy_by_scanning_book_isbn_code),
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            style = MaterialTheme.typography.headlineLarge,
            color = colorResource(id = R.color.dark_brown),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = com.project.feature_auth_module.R.drawable.onboarding_three),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubmitButton(text = "Next", onClick = onNextClick)
    }

}
