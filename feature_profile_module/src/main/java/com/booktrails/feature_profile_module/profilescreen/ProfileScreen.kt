package com.booktrails.feature_profile_module.profilescreen

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
fun ProfileScreen(
    navigateToLogInScreen:() -> Unit,
    navigateToSettingScreen:() -> Unit,
    navigateToContactDevScreen:() -> Unit
) {

    ProfileScreenUI(
        onLogOutClick = {navigateToLogInScreen.invoke()},
        onSettingClick = {navigateToSettingScreen.invoke()},
        onContactDevClick = {navigateToContactDevScreen.invoke()}
    )
}


@Composable
fun ProfileScreenUI(
    onLogOutClick:() -> Unit,
    onSettingClick:() -> Unit,
    onContactDevClick:() -> Unit
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
        text = stringResource(R.string.profile_screen),
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )

    Button(
        onClick = { onSettingClick.invoke() },
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = colorResource(id = R.color.white),

            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            text = stringResource(R.string.settings)
        )
    }
    Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Button(
            onClick = { onLogOutClick.invoke() },
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white),

                ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = stringResource(R.string.logout)
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Button(
            onClick = { onContactDevClick.invoke() },
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white),

                ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = stringResource(R.string.contact_developer)
            )
        }
    }
}