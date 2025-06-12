package com.booktrails.ui_module

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerifyEmailDialog(
    onVerifyEmailClick: () -> Unit,
    onOverlayVisibilityChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .widthIn(max = 320.dp)
            .background(
                color = colorResource(R.color.antique_rose),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.account_either_already_exists_or_not_verified),
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    onOverlayVisibilityChange(false)
                    onVerifyEmailClick.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.verify))
            }
        }
    }
}