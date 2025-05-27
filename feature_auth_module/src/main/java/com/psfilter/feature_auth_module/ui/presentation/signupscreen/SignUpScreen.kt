package com.project.feature_auth_module.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomPasswordTextField
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.ui.AuthFields

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {

/*    @JvmInline
    value class Password(val raw: String)*/ //TODO: use for passwords

    val isLoading = false

    SignUpScreenUI(
        paddingValues = paddingValues,
        isLoading = isLoading,
        onRegisterButtonClick = onRegisterButtonClick,
        onTosClick = onTosClick,
        onPrivacyClick = onPrivacyClick
    )
}

@Composable
fun SignUpScreenUI(
    paddingValues: PaddingValues,
    isLoading: Boolean,
    onRegisterButtonClick: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {


    val loginText = remember { mutableStateOf(AuthFields.Login("")) }
    val emailPlaceholder =  stringResource(R.string.email)
    val emailErrorMessage = "Email cannot be empty" //TODO
    val isEmailInError = false //TODO

    val passwordText = remember { mutableStateOf(AuthFields.Password("")) }
    val passwordPlaceholder =  stringResource(R.string.password)
    val passwordErrorMessage = "Enter Password" //TODO
    val isPasswordInError = false //TODO

    val confirmPassword = remember { mutableStateOf(AuthFields.ConfirmPassword("")) }
    val confirmPasswordPlaceholder =  stringResource(R.string.confirm_password)
    val confirmPasswordErrorMessage = "Enter Password" //TODO

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val nameText = remember { mutableStateOf(AuthFields.Name("")) }
    val namePlaceholder =  stringResource(R.string.name)
    val nameErrorMessage = "Name cannot be empty" //TODO
    val isNameInError = false //TODO

    val buttonText = "Create" //TODO
    val isButtonActive = true //TODO

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.floral_white))
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.signup_ic),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.sign_up_title),
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontSize = 26.sp,
            color = colorResource(id = R.color.dark_brown),
            modifier = Modifier.padding(top = 15.dp, start = 12.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomInputTextField(
            value = loginText.value.raw,
            onValueChange = { loginText.value = AuthFields.Login(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = emailPlaceholder,
            errorMessage = emailErrorMessage,
            isError = isEmailInError
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomInputTextField(
            value = nameText.value.raw,
            onValueChange = { nameText.value = AuthFields.Name(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = namePlaceholder,
            errorMessage = nameErrorMessage,
            isError = isNameInError
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordTextField(
            value = passwordText.value.raw,
            onValueChange = { passwordText.value = AuthFields.Password(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = passwordPlaceholder,
            errorMessage = passwordErrorMessage,
            isError = isPasswordInError
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordTextField(
            value = confirmPassword.value.raw,
            onValueChange = { confirmPassword.value = AuthFields.ConfirmPassword(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = confirmPasswordPlaceholder,
            errorMessage = confirmPasswordErrorMessage,
            isError = isPasswordInError
        )

        Spacer(modifier = Modifier.height(12.dp))

        SubmitButton(buttonText, isButtonActive, {}, )

        Spacer(modifier = Modifier.height(2.dp))

        val annotatedString = buildAnnotatedString {
            append(stringResource(com.project.feature_auth_module.R.string.by_signing_up_you_agree_to_book_trails))
            val robotoRegular = FontFamily(Font(R.font.roboto_regular))

            pushStringAnnotation(tag = stringResource(R.string.tos), annotation = stringResource(R.string.termofservice))
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.antique_rose),
                    fontFamily = robotoRegular,
                    fontWeight = FontWeight.Bold,
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
                    color = colorResource(id = R.color.antique_rose),
                    fontFamily = robotoRegular,
                    fontWeight = FontWeight.Bold,
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

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.antique_rose)
                )
            }
        }
    }

}