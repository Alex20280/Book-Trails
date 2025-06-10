package com.booktrails.ui_module

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onFocusChanged: ((Boolean) -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .height(50.dp)
            .onFocusChanged { focusState ->
                onFocusChanged?.invoke(focusState.isFocused)
            },
        shape = RoundedCornerShape(8.dp),
        isError = isError,
        placeholder = {
            Text(
                text = if (isError) "" else placeholder,
                color = if (isError) colorResource(id = R.color.red) else colorResource(id = R.color.warm_grey),
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(R.drawable.lock_ic),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = colorResource(id = R.color.antique_rose)
                )
            }
        },
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