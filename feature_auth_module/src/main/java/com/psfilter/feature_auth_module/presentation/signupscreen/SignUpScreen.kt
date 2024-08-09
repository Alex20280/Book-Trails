package com.project.feature_auth_module.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.R
import com.psfilter.feature_auth_module.presentation.signupscreen.SignUpViewModel
import com.psfilter.feature_auth_module.domain.validationservice.ConfirmPasswordState
import com.psfilter.feature_auth_module.domain.validationservice.EmailState
import com.psfilter.feature_auth_module.domain.validationservice.PasswordState
import org.koin.androidx.compose.koinViewModel

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

    val loginText = remember { EmailState() }
    val passwordText = remember { PasswordState() }
    val confirmPasswordText = remember { ConfirmPasswordState { passwordText.text } }

    LaunchedEffect(registrationResult) {
        if (registrationResult is RequestResult.Success) {
            onRegisterButtonClick.invoke()
            signUpViewModel.setRegistrationResult(RequestResult.None)
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
        onRegisterButtonClick = {
            signUpViewModel.registerUser(
                loginText.text,
                passwordText.text,
                confirmPasswordText.text
            )
        }, // TODO remove
        onTosClick = onTosClick,
        onPrivacyClick = onPrivacyClick
    )
}

@Composable
fun SignUpScreenUI(
    loginText: EmailState,
    passwordText: PasswordState,
    confirmPasswordText: ConfirmPasswordState,
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
        val annotatedString = buildAnnotatedString {
            append("By signing up, you agree to Book Trails ")
            val robotoRegular = FontFamily(Font(R.font.roboto_regular))

            pushStringAnnotation(
                tag = stringResource(R.string.tos),
                annotation = stringResource(R.string.termofservice)
            )
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

            pushStringAnnotation(
                tag = stringResource(R.string.privacy), annotation = stringResource(
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

        Spacer(modifier = Modifier.height(16.dp))
        ArrowBackImage(onClickBackButton)
        TitleText()
        Animatiion(compositionCongrats)
        Spacer(modifier = Modifier.height(24.dp))
        EmailEditText(loginText)
        Spacer(modifier = Modifier.height(16.dp))
        PasswordEditText(passwordText, confirmPasswordText)
        Spacer(modifier = Modifier.height(8.dp))
        ConfirmPasswordEditText(passwordText, confirmPasswordText)
        Spacer(modifier = Modifier.height(8.dp))
        SubmitButton(
            onClick = onRegisterButtonClick,
            text = stringResource(R.string.sign_up),
            enabled = loginText.isValid() && passwordText.isValid() && confirmPasswordText.isValid()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TermsOfServiceAndPolicy(annotatedString, onTosClick, onPrivacyClick)

    }
}

@Composable
fun TermsOfServiceAndPolicy(
    annotatedString: AnnotatedString,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPasswordEditText(passwordText: PasswordState, confirmPasswordText: ConfirmPasswordState) {
    Column {
        OutlinedTextField(
            value = confirmPasswordText.text,
            onValueChange = {
                confirmPasswordText.text = it
                //confirmPasswordText.validate()
                if (confirmPasswordText.text.isNotEmpty() && passwordText.text.isNotEmpty()) {
                    confirmPasswordText.validate()
                } else {
                    confirmPasswordText.error = null
                }
            },
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
    }
    confirmPasswordText.error?.let { error ->
        ErrorField(error)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEditText(passwordText: PasswordState, confirmPasswordText: ConfirmPasswordState) {
    Column {
        OutlinedTextField(
            value = passwordText.text,
            onValueChange = {
                passwordText.text = it
                if (passwordText.text.isNotEmpty()) {
                    passwordText.validate()
                    confirmPasswordText.validate()
                } else {
                    passwordText.error = null
                    confirmPasswordText.error = null
                }

            },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = R.color.light_grey),
                unfocusedLabelColor = colorResource(id = R.color.light_grey),
                focusedBorderColor = colorResource(id = R.color.light_grey),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                focusedTextColor = colorResource(id = R.color.black),
            ),
            isError = passwordText.error != null
        )
    }
    passwordText.error?.let { error ->
        ErrorField(error)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailEditText(loginText: EmailState) {
    Column {
        OutlinedTextField(
            value = loginText.text,
            onValueChange = {
                loginText.text = it
                if (loginText.text.isNotEmpty()) {
                    loginText.validate()
                } else {
                    loginText.error = null
                }
            },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = R.color.light_grey),
                unfocusedLabelColor = colorResource(id = R.color.light_grey),
                focusedBorderColor = colorResource(id = R.color.light_grey),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                focusedTextColor = colorResource(id = R.color.black),
            ),
            isError = loginText.error != null
        )
        loginText.error?.let { error ->
            ErrorField(error)
        }
    }

}

@Composable
fun ErrorField(errorText: String) {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = errorText,
        //color = colorResource(id = R.color.red)
        style = MaterialTheme.typography.bodySmall.copy(color = colorResource(id = R.color.error_color))
    )
}


@Composable
fun Animatiion(compositionCongrats: LottieComposition?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = compositionCongrats,
            iterations = 10
        )
    }
}

@Composable
fun TitleText() {
    Text(
        modifier = Modifier.padding(top = 38.dp),
        text = stringResource(R.string.registers),
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
fun ArrowBackImage(onClickBackButton: () -> Unit) {
    Image(
        modifier = Modifier
            .clickable { onClickBackButton.invoke() }
            .size(24.dp),
        painter = painterResource(id = R.drawable.arrow_back_icon),
        contentDescription = stringResource(R.string.arrow_back)
    )
}
