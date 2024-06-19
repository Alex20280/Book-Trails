package com.project.feature_auth_module.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R

@Composable
fun ForgetPasswordScreen(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRestorePasswordClick: () -> Unit,
) {

    /*    val viewModel = hiltViewModel<DetailsViewModel>()
        val screenDetails = viewModel.feedDetails.collectAsState()*/

    ForgetPasswordScreenUI(
        paddingValues = paddingValues,
        onClickBackButton = onClickBackButton,
        onRestorePasswordClick = onRestorePasswordClick
        /*        screenDetails = screenDetails,
                onTextClicked = {navigate(screenDetails.value.link?.toUri()?.toString() ?: "")},
                onIconClicked = {viewModel.toggleBookmark(it)}*/
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreenUI(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRestorePasswordClick: () -> Unit
) {

    val emailText = remember { mutableStateOf("") }

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
            painter = painterResource(id = com.project.feature_auth_module.R.drawable.arrow_back_icon),
            contentDescription = stringResource(com.project.feature_auth_module.R.string.arrow_back)
        )

        Text(
            modifier = Modifier.padding(top = 38.dp),
            text = stringResource(com.project.feature_auth_module.R.string.reset_password),
            style = MaterialTheme.typography.headlineLarge,
         )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(com.project.feature_auth_module.R.string.type_your_email_and_we_will_send_a_link_to_reset_your_password),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = emailText.value,
            onValueChange = { emailText.value = it },
            label = { Text(stringResource(com.project.feature_auth_module.R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = R.color.light_grey),
                unfocusedLabelColor = colorResource(id = R.color.light_grey),
                focusedBorderColor = colorResource(id = R.color.light_grey),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                focusedTextColor = colorResource(id = R.color.black),
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onRestorePasswordClick.invoke() },
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
                text = stringResource(com.project.feature_auth_module.R.string.send_email))
        }

    }
}