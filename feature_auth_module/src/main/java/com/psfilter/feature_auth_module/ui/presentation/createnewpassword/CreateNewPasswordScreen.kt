package com.psfilter.feature_auth_module.ui.presentation.createnewpassword

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.CustomPasswordTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.ui.AuthFields

@Composable
fun CreateNewPasswordScreen(
    paddingValues: PaddingValues,
    verificationCode: String,
    onSaveClick: () -> Unit
) {

    CreateNewPasswordScreenUI(
        paddingValues = paddingValues,
        verificationCode = verificationCode,
        onSaveClick = onSaveClick,
    )
}

@Composable
fun CreateNewPasswordScreenUI(
    paddingValues: PaddingValues,
    verificationCode: String,
    onSaveClick: () -> Unit,
) {

    val verificationCodeState = remember { mutableStateOf(AuthFields.VerificationCode(verificationCode.toString())) }
    val verificationPlaceholder = "Verification Code"
    Log.d("MyCode", verificationCodeState.value.raw)

    val passwordText = remember { mutableStateOf(AuthFields.Password("")) }
    val passwordPlaceholder =  stringResource(R.string.password)
    val isPasswordInError = false //TODO

    val confirmPassword = remember { mutableStateOf(AuthFields.ConfirmPassword("")) }
    val confirmPasswordPlaceholder =  stringResource(R.string.confirm_password)
    val isConfirmPasswordInError = false //TODO

    val buttonText = "Save" //TODO
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
            painter = painterResource(id = R.drawable.lock_large_ic),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Create New Password",
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontSize = 26.sp,
            color = colorResource(id = R.color.dark_brown),
            modifier = Modifier.padding(top = 15.dp, start = 12.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomInputTextField(
            value = verificationCodeState.value.raw, // Access the String value
            onValueChange = { newValue -> verificationCodeState.value = AuthFields.VerificationCode(newValue) },modifier = Modifier.fillMaxWidth(),
            placeholder = verificationPlaceholder,
            isError = null
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordTextField(
            value = passwordText.value.raw,
            onValueChange = { passwordText.value = AuthFields.Password(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = passwordPlaceholder,
            isError = isPasswordInError
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordTextField(
            value = confirmPassword.value.raw,
            onValueChange = { confirmPassword.value = AuthFields.ConfirmPassword(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = confirmPasswordPlaceholder,
            isError = isConfirmPasswordInError
        )


        Spacer(modifier = Modifier.height(12.dp))

        SubmitButton(buttonText, isButtonActive, {onSaveClick.invoke()}, )
    }

}