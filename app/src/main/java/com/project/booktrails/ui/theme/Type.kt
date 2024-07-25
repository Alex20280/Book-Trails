package com.project.booktrails.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.project.booktrails.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(com.booktrails.ui_module.R.font.roboto_bold)),
        fontWeight = FontWeight.W500,
        fontSize = 26.sp,
        color = Color.Black
    ),

    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(com.booktrails.ui_module.R.font.roboto_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = Color.Black
    ),

    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(com.booktrails.ui_module.R.font.roboto_medium)),
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        color = Color.White
    ),

    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(com.booktrails.ui_module.R.font.roboto_medium)),
        fontWeight = FontWeight.W800,
        fontSize = 16.sp,
        color = Color.White
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)