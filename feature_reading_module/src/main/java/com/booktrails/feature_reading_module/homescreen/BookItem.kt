package com.booktrails.feature_reading_module.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.booktrails.feature_reading_module.R
import com.booktrails.feature_reading_module.homescreen.model.ReadStatus

@Composable
fun BookItem(
    imageRes: Int,
    title: String,
    author: String,
    pages: Int,
    readStatus: ReadStatus,
    rating: Int,
    onItemClick: () -> Unit
) {

    val hideRow = readStatus == ReadStatus.TO_READ
    val statusColor = when (readStatus) {
        ReadStatus.READ -> colorResource(id = com.booktrails.ui_module.R.color.green)
        ReadStatus.TO_READ -> colorResource(id = com.booktrails.ui_module.R.color.light_grey)
        ReadStatus.READING -> colorResource(id = com.booktrails.ui_module.R.color.blue)
    }

    Row(
        modifier = Modifier
            .clickable { onItemClick.invoke() }
            .background(
                color = colorResource(id = com.booktrails.ui_module.R.color.very_light_grey),
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth()
            .height(120.dp)
    ) {
        // Left image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = stringResource(R.string.item_image),
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
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
                if (!hideRow) {
                    Row {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (index < rating) colorResource(id = com.booktrails.ui_module.R.color.yellow) else colorResource(
                                    id = com.booktrails.ui_module.R.color.grey
                                ),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Author
            Text(
                text = author,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                    .copy(fontStyle = FontStyle.Italic),
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
                    text = pages.toString() + stringResource(R.string.pages),
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp).copy(
                        color = colorResource(
                            id = com.booktrails.ui_module.R.color.black
                        )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = readStatus.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                        .copy(color = statusColor)
                )
            }
        }
    }
}