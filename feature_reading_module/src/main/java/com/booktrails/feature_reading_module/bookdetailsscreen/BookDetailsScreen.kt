package com.booktrails.feature_reading_module.bookdetailsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
fun BookDetailsScreen(
     idArgs: Int,
     navigateToReadingTimerScreen: (Int) -> Unit,
) {

    BookDetailsScreenUI(
        idArgs = idArgs,
        onStartReading = {navigateToReadingTimerScreen.invoke(idArgs)}
    )
}

@Composable
fun BookDetailsScreenUI(
    idArgs: Int,
    onStartReading:() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 50.dp, end = 16.dp)
    ) {
    Text(
        text = "Book Details $idArgs",
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp).copy(
            color = colorResource(
                id = R.color.grey
            )
        )
    )

        Button(
            onClick = { onStartReading.invoke() },
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
                text = stringResource(com.booktrails.feature_reading_module.R.string.start_continue_reading)
            )
        }
    }
}