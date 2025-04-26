package com.booktrails.feature_profile_module.badgesscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R
import com.booktrails.ui_module.TopBarBackground

@Composable
fun BadgesScreen(

) {

    BadgesScreenUI(

    )
}


@Composable
fun BadgesScreenUI(

) {
    TopBarBackground()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 46.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {


    Text(
        text = stringResource(R.string.badges_screen),
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )



    }
}