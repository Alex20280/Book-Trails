package com.booktrails.feature_profile_module.contactdevscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R
import com.booktrails.ui_module.TopBarBackground

class ContactDeveloperScreen {
}

@Composable
fun ContactDeveloperScreen(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit
) {

    ContactDeveloperScreenUI(
        paddingValues = paddingValues,
        onClickBackButton = { onClickBackButton.invoke() }
    )
}


@Composable
fun ContactDeveloperScreenUI(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit
) {
    TopBarBackground()

    Text(
        text = stringResource(R.string.contact_developer),
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = com.booktrails.ui_module.R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding()
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        //TODO: Bug - when click twice go back to splash screen
        Image(
            modifier = Modifier
                .clickable { onClickBackButton.invoke() }
                .size(24.dp),
            painter = painterResource(id = R.drawable.arrow_back_icon),
            contentDescription = stringResource(R.string.arrow_back)
        )
    }


}