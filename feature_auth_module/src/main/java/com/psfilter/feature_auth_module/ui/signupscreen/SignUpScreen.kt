package com.project.feature_auth_module.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.feature_auth_module.R

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {

    /*    val viewModel = hiltViewModel<DetailsViewModel>()
        val screenDetails = viewModel.feedDetails.collectAsState()*/

    SignUpScreenUI(
        paddingValues = paddingValues,
        onClickBackButton = onClickBackButton,
        onRegisterButtonClick = onRegisterButtonClick,
        onTosClick = onTosClick,
        onPrivacyClick = onPrivacyClick
        /*        screenDetails = screenDetails,
                onTextClicked = {navigate(screenDetails.value.link?.toUri()?.toString() ?: "")},
                onIconClicked = {viewModel.toggleBookmark(it)}*/
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenUI(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {

    val loginText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    val confirmPasswordText = remember { mutableStateOf("") }

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

        Image(
            modifier = Modifier
                .clickable { onClickBackButton.invoke() }
                .size(24.dp),
            painter = painterResource(id = R.drawable.arrow_back_icon),
            contentDescription = stringResource(R.string.arrow_back)
        )

        Text(
            modifier = Modifier.padding(top = 38.dp),
            text = stringResource(R.string.register),
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = loginText.value,
            onValueChange = { loginText.value = it },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedTextColor = colorResource(id = com.booktrails.ui_module.R.color.black),
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordText.value,
            onValueChange = { passwordText.value = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedTextColor = colorResource(id = com.booktrails.ui_module.R.color.black),
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPasswordText.value,
            onValueChange = { confirmPasswordText.value = it },
            label = { Text(stringResource(R.string.confirm_password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedTextColor = colorResource(id = com.booktrails.ui_module.R.color.black),
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onRegisterButtonClick.invoke() },
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = com.booktrails.ui_module.R.color.blue),
                contentColor = colorResource(id = com.booktrails.ui_module.R.color.white),

                ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = stringResource(R.string.sign_up)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val annotatedString = buildAnnotatedString {
            append("By signing up, you agree to Book Trails ")
            val robotoRegular = FontFamily(Font(com.booktrails.ui_module.R.font.roboto_regular))

            pushStringAnnotation(tag = "TOS", annotation = "TermOfService")
            withStyle(style = SpanStyle(color = colorResource(id = com.booktrails.ui_module.R.color.blue), fontFamily = robotoRegular, textDecoration = TextDecoration.Underline)) {
                append("Term of Service")
            }
            pop()

            append(" and ")

            pushStringAnnotation(tag = "PRIVACY", annotation = "PrivacyPolicy")
            withStyle(style = SpanStyle(color = colorResource(id = com.booktrails.ui_module.R.color.blue), fontFamily = robotoRegular, textDecoration = TextDecoration.Underline)) {
                append("Privacy Policy")
            }
            pop()
        }

        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "TOS", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onTosClick.invoke()
                    }
                annotatedString.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onPrivacyClick.invoke()
                    }
            }
        )

    }
}