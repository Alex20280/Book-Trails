package com.booktrails.ui_module

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp

@Composable
fun NextButton(
    text: String,
    onClick: () -> Unit
) {
    //var isPressed by remember { mutableStateOf(false) }
    val antiqueRose = colorResource(id = R.color.antique_rose)
    val darkBrown = colorResource(id = R.color.dark_brown)
    val offsetX by animateDpAsState(
        targetValue =  0.dp,
        animationSpec = tween(durationMillis = 200)
    )
    val density = LocalDensity.current
    val offsetPx = with(density) { IntOffset(x = offsetX.roundToPx(), y = 0) }

    Box(
        modifier = Modifier
            .width(120.dp)
            .height(42.dp)
            .clickable {
                //isPressed = true
                Log.d("MyClick", "myClick")
                onClick.invoke()
                //isPressed = false
            }
            .background(antiqueRose, RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(116.dp)
                .height(40.dp)
                .background(antiqueRose, RoundedCornerShape(20.dp))
                .offset { offsetPx }
        ) {
            Text(
                text = text,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 26.dp)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 0.dp)
                    .size(40.dp)
                    .background(darkBrown, RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_forward),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}