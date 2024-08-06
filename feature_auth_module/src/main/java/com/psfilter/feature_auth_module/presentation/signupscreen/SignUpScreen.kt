package com.project.feature_auth_module.ui

import android.widget.Toast
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.R
import com.psfilter.feature_auth_module.presentation.onboarding.OnboardingViewModel
import com.psfilter.feature_auth_module.presentation.signupscreen.SignUpViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Error

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {

    val signUpViewModel: SignUpViewModel = koinViewModel()
    val errorMessage by signUpViewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    val registrationResult by signUpViewModel.registrationResult.collectAsState()

    val loginText = rememberSaveable { mutableStateOf("") }
    val passwordText = rememberSaveable { mutableStateOf("") }
    val confirmPasswordText = rememberSaveable { mutableStateOf("") }


    LaunchedEffect(registrationResult) {
        if (registrationResult is RequestResult.Success) {
            onRegisterButtonClick.invoke()
        }
    }

    errorMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }


    SignUpScreenUI(
        loginText = loginText,
        passwordText = passwordText,
        confirmPasswordText = confirmPasswordText,
        paddingValues = paddingValues,
        onClickBackButton = onClickBackButton,
        onRegisterButtonClick = {signUpViewModel.registerUser(loginText.value, passwordText.value, confirmPasswordText.value)}, // TODO remove
        onTosClick = onTosClick,
        onPrivacyClick = onPrivacyClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenUI(
    loginText: MutableState<String>,
    passwordText: MutableState<String>,
    confirmPasswordText: MutableState<String>,
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {

    val compositionCongrats by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.sign_up_animation)
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

        Image(
            modifier = Modifier
                .clickable { onClickBackButton.invoke() }
                .size(24.dp),
            painter = painterResource(id = R.drawable.arrow_back_icon),
            contentDescription = stringResource(R.string.arrow_back)
        )

        Text(
            modifier = Modifier.padding(top = 38.dp),
            text = stringResource(R.string.registers),
            style = MaterialTheme.typography.headlineLarge,
        )

        LottieAnimation(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            composition = compositionCongrats, iterations = 10)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = loginText.value,
            onValueChange = { loginText.value = it },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = R.color.light_grey),
                unfocusedLabelColor = colorResource(id = R.color.light_grey),
                focusedBorderColor = colorResource(id = R.color.light_grey),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                focusedTextColor = colorResource(id = R.color.black),
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
                focusedLabelColor = colorResource(id = R.color.light_grey),
                unfocusedLabelColor = colorResource(id = R.color.light_grey),
                focusedBorderColor = colorResource(id = R.color.light_grey),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                focusedTextColor = colorResource(id = R.color.black),
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
                focusedLabelColor = colorResource(id = R.color.light_grey),
                unfocusedLabelColor = colorResource(id = R.color.light_grey),
                focusedBorderColor = colorResource(id = R.color.light_grey),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                focusedTextColor = colorResource(id = R.color.black),
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        SubmitButton(
            onClick = onRegisterButtonClick,
            text = stringResource(R.string.sign_up)
        )

        Spacer(modifier = Modifier.height(8.dp))

        val annotatedString = buildAnnotatedString {
            append("By signing up, you agree to Book Trails ")
            val robotoRegular = FontFamily(Font(R.font.roboto_regular))

            pushStringAnnotation(tag = stringResource(R.string.tos), annotation = stringResource(R.string.termofservice))
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = com.booktrails.ui_module.R.color.blue),
                    fontFamily = robotoRegular,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(stringResource(R.string.term_of_service))
            }
            pop()

            append(stringResource(R.string.and))

            pushStringAnnotation(tag = stringResource(R.string.privacy), annotation = stringResource(
                R.string.privacypolicy
            )
            )
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.blue),
                    fontFamily = robotoRegular,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(stringResource(R.string.privacy_policy))
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