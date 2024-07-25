package com.booktrails.feature_book_management_module.addbookscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.R

@Composable
fun AddBookScreen(
    navigateToIsbnScanScreen: () -> Unit,
    navigateToManualAddScreen: () -> Unit,
) {

    AddBookScreenUI(
        onScanClick = {navigateToIsbnScanScreen.invoke()},
        onManualAddClick = {navigateToManualAddScreen.invoke()},
    )
}

@Composable
fun AddBookScreenUI(
    onScanClick:() -> Unit,
    onManualAddClick:() -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = 30.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(com.booktrails.feature_book_management_module.R.string.add_book_screen),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp).copy(
                color = colorResource(
                    id = R.color.grey
                )
            )
        )

        Button(
            onClick = { onScanClick.invoke() },
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white),

                ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = "Scan"
            )
        }

        Button(
            onClick = { onManualAddClick.invoke() },
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white),

                ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = stringResource(com.booktrails.feature_book_management_module.R.string.manual_add)
            )
        }
    }
}