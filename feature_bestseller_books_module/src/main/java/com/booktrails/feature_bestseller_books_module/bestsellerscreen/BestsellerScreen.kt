package com.booktrails.feature_bestseller_books_module.bestsellerscreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.booktrails.feature_bestseller_books_module.bestsellerscreen.model.BestsellerModel
import com.booktrails.ui_module.R
import com.booktrails.ui_module.TopBarBackground

@Composable
fun BestsellerScreen(
    navigateToBestsellerDetailScreen:(Int) -> Unit
) {

    val bookList = listOf(
        BestsellerModel(
            id = 1,
            imageRes = R.drawable.potter_image,
            title = "1984",
            author = "George Orwell",
            rank = 25
        ),
        BestsellerModel(
            id = 2,
            imageRes = R.drawable.ulysses_image,
            title = "Ulysses",
            author = "James Joyce",
            rank = 75
        ),
        BestsellerModel(
            id = 3,
            imageRes = R.drawable.robinson_crusoe_image,
            title = "Robinson Crusoe",
            author = "Daniel Defoe",
            rank = 20
        ),
        BestsellerModel(
            id = 4,
            imageRes = R.drawable.plague_image,
            title = "Plague",
            author = "Albert Camus",
            rank = 31
        )
    )

    BestsellerScreenUI(
        bookList = bookList,
        navigate = {navigateToBestsellerDetailScreen(it)}
    )
}


@Composable
fun BestsellerScreenUI(
    bookList: List<BestsellerModel>,
    navigate:(Int) -> Unit
) {
    TopBarBackground()

    Text(
        text = "Bestseller screen",
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )

    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
        items(bookList.size) { index ->
            val book = bookList[index]
            BestsellerItem(
                imageRes = book.imageRes,
                title = book.title,
                author = book.author,
                rank = book.rank,
                onBestSellerItemClick = {navigate.invoke(book.id)}
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}