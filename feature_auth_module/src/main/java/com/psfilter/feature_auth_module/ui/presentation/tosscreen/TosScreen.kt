package com.psfilter.feature_auth_module.ui.presentation.tosscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.R
import com.booktrails.ui_module.TopBarBackground

@Composable
fun TosScreen(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit
) {

    TosScreenUI(
        paddingValues = paddingValues,
        onClickBackButton = { onClickBackButton.invoke() }
    )
}

@Composable
fun TosScreenUI(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onClickBackButton.invoke() },
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = stringResource(R.string.arrow_back)
        )

        Text(
            text = stringResource(R.string.termofservice),
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontSize = 20.sp,
            color = colorResource(id = R.color.dark_brown),
        )
    }
}