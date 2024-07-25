package com.booktrails.ui_module

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ToolBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.clickable {  }, //TODO: menu icon click
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = "menu icon",
        )

        Text(
            text = title,
            style =  MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center
        )

        Row {
            Image(
                modifier = Modifier.clickable {  }, //TODO: search icon click
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "search icon"
            )
            Image(
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = "filter icon",
                modifier = Modifier.padding(start = 16.dp)
                    .clickable {  }
            )
        }
    }
}