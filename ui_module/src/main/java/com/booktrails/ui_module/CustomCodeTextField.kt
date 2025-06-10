package com.booktrails.ui_module

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomCodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= 4 && newValue.all { it.isDigit() }) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = colorResource(id = R.color.warm_grey),
                    modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
                )
            },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center), // Center the text
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.antique_rose),
                unfocusedTextColor = colorResource(id = R.color.antique_rose),
                focusedContainerColor = colorResource(id = R.color.floral_white),
                unfocusedContainerColor = colorResource(id = R.color.floral_white),
                errorContainerColor = colorResource(id = R.color.floral_white),
                focusedIndicatorColor = colorResource(id = R.color.antique_rose),
                unfocusedIndicatorColor = colorResource(id = R.color.antique_rose),
                errorIndicatorColor = colorResource(id = R.color.red),
                errorTextColor = colorResource(id = R.color.red),
                errorPlaceholderColor = colorResource(id = R.color.red),
                unfocusedPlaceholderColor = colorResource(id = R.color.warm_grey),
                cursorColor = colorResource(id = R.color.antique_rose),
                selectionColors = TextSelectionColors(
                    handleColor = colorResource(id = R.color.antique_rose),
                    backgroundColor = colorResource(id = R.color.antique_rose).copy(alpha = 0.4f)
                )
            )
        )
    }
}