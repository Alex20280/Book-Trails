package com.project.feature_auth_module.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.ui.AuthFields

@Composable
fun ForgetPasswordScreen(
    paddingValues: PaddingValues,
    onRestorePasswordClick: () -> Unit,
    onCreateAccountClick: (Int) -> Unit,
) {

    /*    val viewModel = hiltViewModel<DetailsViewModel>()
        val screenDetails = viewModel.feedDetails.collectAsState()*/

    ForgetPasswordScreenUI(
        paddingValues = paddingValues,
        onSendCodeAgainClick = {},
        onRestoreClick = {},
        onCreateAccountClick = onCreateAccountClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreenUI(
    paddingValues: PaddingValues,
    onSendCodeAgainClick: () -> Unit,
    onRestoreClick: () -> Unit,
    onCreateAccountClick: (Int) -> Unit,
) {

    var isAccountNotFoundOverlayVisible by remember { mutableStateOf(false) }

    val emailText = remember { mutableStateOf(AuthFields.Login("")) }
    val loginPlaceholder = stringResource(R.string.login)
    val isLoginInError = false //TODO

    val verificationCode = remember { mutableStateOf(AuthFields.VerificationCode("")) }
    val verificationPlaceholder = "Verification Code"
    val verificationMessage = "Request was sent out" //TODO
    val isVerificationCodeSentOut = false //TODO

    val buttonText = "Restore" //TODO
    val isButtonActive = true //TODO

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.floral_white))
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
                contentAlignment = Alignment.Center

    ) {

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lock_large_ic),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = stringResource(R.string.reset_password),
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontSize = 26.sp,
                color = colorResource(id = R.color.dark_brown),
                modifier = Modifier.padding(top = 10.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomInputTextField(
                value = emailText.value.raw,
                onValueChange = { emailText.value = AuthFields.Login(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp), // Explicitly remove bottom padding
                placeholder = loginPlaceholder,
                isError = isLoginInError,
                borderTint = isVerificationCodeSentOut
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 10.dp, end = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (isVerificationCodeSentOut) {
                    Text(
                        text = verificationMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.salad_green),
                        modifier = Modifier.weight(1f)
                    )
                }
                Text(
                    text = stringResource(com.project.feature_auth_module.R.string.send_code_again),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    ),
                    color = colorResource(id = R.color.antique_rose),
                    modifier = Modifier
                        .clickable { onSendCodeAgainClick.invoke() }
                        .padding(0.dp),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            CustomInputTextField(
                value = verificationCode.value.raw,
                onValueChange = { verificationCode.value = AuthFields.VerificationCode(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = verificationPlaceholder,
                isError = isLoginInError
            )

            Spacer(modifier = Modifier.height(25.dp))

            SubmitButton(
                 buttonText,
                isButtonActive,
                onClick = {
                    if (!isAccountNotFoundOverlayVisible) {
                        isAccountNotFoundOverlayVisible = true

                    }
                }
            )
        }

        if (isAccountNotFoundOverlayVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .blur(radius = 32.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                contentAlignment = Alignment.Center
            ) {
                AccountNotFoundCard(
                    onCreateAccountClick = {
                        onCreateAccountClick.invoke(verificationCode.value.raw.toInt())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onOverlayVisibilityChange = { isAccountNotFoundOverlayVisible = it }
                )
            }
        }
    }
}

@Composable
fun AccountNotFoundCard(
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
    onOverlayVisibilityChange: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.antique_rose),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(com.project.feature_auth_module.R.string.account_not_found_click_below_to_create_a_new_one),
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    onOverlayVisibilityChange(false)
                    onCreateAccountClick.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(com.project.feature_auth_module.R.string.create_account))
            }
        }
    }
}
