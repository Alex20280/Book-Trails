package com.booktrails.feature_profile_module

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R

@Composable
fun ProfileScreen(
) {

    /*    val viewModel = hiltViewModel<DetailsViewModel>()
        val screenDetails = viewModel.feedDetails.collectAsState()*/

    ProfileScreennUI(
        /*        screenDetails = screenDetails,
                onTextClicked = {navigate(screenDetails.value.link?.toUri()?.toString() ?: "")},
                onIconClicked = {viewModel.toggleBookmark(it)}*/
    )
}


@Composable
fun ProfileScreennUI(
) {
    Text(
        text = "Profile Screenn",
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )

}