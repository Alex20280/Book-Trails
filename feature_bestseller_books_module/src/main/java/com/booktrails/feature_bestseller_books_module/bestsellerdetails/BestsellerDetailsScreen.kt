package com.booktrails.feature_bestseller_books_module.bestsellerdetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.R

@Composable
fun BestsellerDetailsScreen(
    bestsellerIdArgs: Int
) {

    BestsellerDetailsScreenUI(
        bestsellerIdArgs = bestsellerIdArgs
    )
}

@Composable
fun BestsellerDetailsScreenUI(
    bestsellerIdArgs: Int
) {
    Text(
        text = "BestsellerDetails Screen $bestsellerIdArgs",
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )

}