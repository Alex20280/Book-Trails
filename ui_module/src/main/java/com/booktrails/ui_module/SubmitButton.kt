package com.booktrails.ui_module

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubmitButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isActive) {
        Color.White
    } else {
        Color.Gray
    }

    val activeBtnColor = colorResource(R.color.antique_rose)
    val inActiveBtnColor = colorResource(R.color.dark_antique_rose)

    Button(
        onClick = onClick,
        enabled = isActive,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) activeBtnColor else inActiveBtnColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .width(170.dp)
            .height(55.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(7.dp)
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.roboto_medium)),
            fontSize = 16.sp,
            text = text,
            color = colorResource(R.color.white)
        )
    }
}