package com.project.feature_auth_module

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    paddingValues: PaddingValues,
    navigate: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        navigate.invoke()
    }

    /*    val viewModel = hiltViewModel<DetailsViewModel>()
        val screenDetails = viewModel.feedDetails.collectAsState()*/

    SplashScreenUI(
        paddingValues = paddingValues,
        /*        screenDetails = screenDetails,
                onTextClicked = {navigate(screenDetails.value.link?.toUri()?.toString() ?: "")},
                onIconClicked = {viewModel.toggleBookmark(it)}*/
    )
}


@Composable
fun SplashScreenUI(
    paddingValues: PaddingValues
) {


    Column(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .fillMaxSize()
            .background(colorResource(id = com.booktrails.ui_module.R.color.blue)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            style = MaterialTheme.typography.labelMedium.copy(color = colorResource(id = R.color.white)).copy(fontSize = 24.sp),
            text = stringResource(com.project.feature_auth_module.R.string.logo)
        )

    }
}


