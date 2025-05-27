package com.booktrails.ui_module

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomInputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean? = false,
    borderTint: Boolean = false
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        isError = isError == true,
        placeholder = {
            Text(
                text = if (isError == true && !errorMessage.isNullOrEmpty()) errorMessage else placeholder,
                color = if (isError == true) colorResource(id = R.color.red) else colorResource(id = R.color.warm_grey)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(id = R.color.antique_rose),
            unfocusedTextColor = colorResource(id = R.color.antique_rose),

            focusedContainerColor = colorResource(id = R.color.floral_white),
            unfocusedContainerColor = colorResource(id = R.color.floral_white),
            errorContainerColor = colorResource(id = R.color.floral_white),

            focusedIndicatorColor = if (borderTint) {
                colorResource(id = R.color.salad_green)
            } else {
                colorResource(id = R.color.antique_rose)
            },
            unfocusedIndicatorColor = if (borderTint) {
                colorResource(id = R.color.salad_green)
            } else {
                colorResource(id = R.color.antique_rose)
            },

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