package com.booktrails.feature_reading_module.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.booktrails.ui_module.R
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.booktrails.feature_reading_module.homescreen.model.Books
import com.booktrails.feature_reading_module.homescreen.model.ReadStatus
import com.booktrails.ui_module.TopBarBackground
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.booktrails.core_module.errorhandling.RequestResult
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navigateToDetailsScreen: (Int) -> Unit,
    navigateToAddBookScreen: () -> Unit,
) {

/*    val bookList = listOf(
        Books(
            id = 1,
            imageRes = R.drawable.potter_image,
            title = "1984",
            author = "George Orwell",
            pages = 328,
            readStatus = ReadStatus.READING,
            rating = 4
        ),
        Books(
            id = 2,
            imageRes = R.drawable.ulysses_image,
            title = "Ulysses",
            author = "James Joyce",
            pages = 1200,
            readStatus = ReadStatus.READ,
            rating = 4
        ),
        Books(
            id = 3,
            imageRes = R.drawable.robinson_crusoe_image,
            title = "Robinson Crusoe",
            author = "Daniel Defoe",
            pages = 260,
            readStatus = ReadStatus.READING,
            rating = 0
        ),
        Books(
            id = 4,
            imageRes = R.drawable.plague_image,
            title = "Plague",
            author = "Albert Camus",
            pages = 200,
            readStatus = ReadStatus.TO_READ,
            rating = 0
        ),
        Books(
            id = 1,
            imageRes = R.drawable.potter_image,
            title = "1984",
            author = "George Orwell",
            pages = 328,
            readStatus = ReadStatus.READING,
            rating = 4
        ),
        Books(
            id = 2,
            imageRes = R.drawable.ulysses_image,
            title = "Ulysses",
            author = "James Joyce",
            pages = 1200,
            readStatus = ReadStatus.READ,
            rating = 4
        ),
        Books(
            id = 3,
            imageRes = R.drawable.robinson_crusoe_image,
            title = "Robinson Crusoe",
            author = "Daniel Defoe",
            pages = 260,
            readStatus = ReadStatus.READING,
            rating = 0
        ),
        Books(
            id = 4,
            imageRes = R.drawable.plague_image,
            title = "Plague",
            author = "Albert Camus",
            pages = 200,
            readStatus = ReadStatus.TO_READ,
            rating = 0
        )
    )*/
    val homeScreenViewModel: HomeScreenViewModel = koinViewModel()
    val bookList by homeScreenViewModel.bookList.collectAsState()


    LoginScreenUI(
        bookList = bookList,
        onFabClick = {navigateToAddBookScreen.invoke()},
        onItemClick = {navigateToDetailsScreen.invoke(it)},
        onContinueClick = {navigateToDetailsScreen.invoke(it)},
    )
}

@Composable
fun LoginScreenUI(
    bookList: List<Books>,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onContinueClick: (Int) -> Unit,
) {

    val paddingValues = WindowInsets.navigationBars.asPaddingValues()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Reading", "Read", "To Read")
    val currentlyReadingBook = remember(bookList) {  //TODO check
        bookList.find { it.readStatus == ReadStatus.READING }
    }

    TopBarBackground()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 10.dp)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                currentlyReadingBook?.imageRes?.let { painterResource(id = it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = stringResource(com.booktrails.feature_reading_module.R.string.recent_book_image),
                        modifier = Modifier
                            .size(width = 100.dp, height = 150.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 50.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(com.booktrails.feature_reading_module.R.string.recently_read),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp).copy(
                            color = colorResource(
                                id = R.color.grey
                            )
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = currentlyReadingBook?.title.toString(),
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 20.sp).copy(
                            color = colorResource(
                                R.color.black
                            )
                        ),
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = currentlyReadingBook?.author.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                            .copy(fontStyle = FontStyle.Italic),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            modifier = Modifier.clickable {
                                currentlyReadingBook?.id?.let {
                                    onContinueClick.invoke(it)
                                }
                            },
                            text = stringResource(com.booktrails.feature_reading_module.R.string.continueRead),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp).copy(
                                color = colorResource(id = R.color.blue)
                            )
                        )

                        Image(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            painter = painterResource(id = R.drawable.arrow_forward_icon),
                            contentDescription = stringResource(com.booktrails.feature_reading_module.R.string.arrow_forward),
                        )
                    }

                }
            }

            Column {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(  //Indicator
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = colorResource(id = R.color.blue),
                            height = 3.dp // You can customize the height of the indicator
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selectedContentColor = colorResource(id = R.color.black),
                            unselectedContentColor = colorResource(id = R.color.black),
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(text = title) }
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> BookList(bookList, onItemClick)
                    1 -> BookList(bookList.filter { it.readStatus == ReadStatus.READING }, onItemClick)
                    2 -> BookList(bookList.filter { it.readStatus == ReadStatus.READ }, onItemClick)
                    3 -> BookList(bookList.filter { it.readStatus == ReadStatus.TO_READ }, onItemClick)
                }
            }
        }

        FloatingActionButton(
            onClick = { onFabClick.invoke() },
            containerColor = colorResource(id = R.color.blue),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = paddingValues.calculateBottomPadding() + 65.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

    }
}

@Composable
fun BookList(books: List<Books>, onItemClick: (Int) -> Unit) {
    val paddingValues = WindowInsets.navigationBars.asPaddingValues()

    LazyColumn(modifier = Modifier.padding(top = 10.dp, bottom = paddingValues.calculateBottomPadding()+45.dp)) {
        items(books.size) { index ->
            val book = books[index]
            BookItem(
                imageRes = book.imageRes,
                title = book.title,
                author = book.author,
                pages = book.pages,
                readStatus = book.readStatus,
                rating = book.rating,
                onItemClick = { onItemClick.invoke(book.id) }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}