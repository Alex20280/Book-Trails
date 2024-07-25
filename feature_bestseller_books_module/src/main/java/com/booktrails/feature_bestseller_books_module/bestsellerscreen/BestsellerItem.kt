package com.booktrails.feature_bestseller_books_module.bestsellerscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.R

@Composable
fun BestsellerItem(
    imageRes: Int,
    title: String,
    author: String,
    rank: Int,
    onBestSellerItemClick:() -> Unit
) {

    Row(
        modifier = Modifier
            .clickable { onBestSellerItemClick.invoke() }
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.very_light_grey))
            .height(120.dp)
    ) {
        // Left image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = stringResource(com.booktrails.feature_bestseller_books_module.R.string.item_image),
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight(),
            contentScale = ContentScale.Crop
        )

        // Right content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            // Title and rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Author
            Text(
                text = author,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp).copy(fontStyle = FontStyle.Italic),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Pages and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = rank.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp).copy(color = colorResource(
                        id = R.color.black
                    )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

            }
        }
    }
}