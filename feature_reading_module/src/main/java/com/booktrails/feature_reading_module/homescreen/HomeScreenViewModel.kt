package com.booktrails.feature_reading_module.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrails.feature_reading_module.homescreen.model.Books
import com.booktrails.feature_reading_module.homescreen.model.ReadStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel (
) : ViewModel() {

    private val _bookList = MutableStateFlow<List<Books>>(emptyList())
    val bookList: StateFlow<List<Books>> = _bookList

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            // Simulate a data fetch or any async operation
            _bookList.value = listOf(
                Books(
                    id = 1,
                    imageRes = com.booktrails.ui_module.R.drawable.potter_image,
                    title = "1984",
                    author = "George Orwell",
                    pages = 328,
                    readStatus = ReadStatus.READING,
                    rating = 4
                ),
                Books(
                    id = 2,
                    imageRes = com.booktrails.ui_module.R.drawable.ulysses_image,
                    title = "Ulysses",
                    author = "James Joyce",
                    pages = 1200,
                    readStatus = ReadStatus.READ,
                    rating = 4
                ),
                Books(
                    id = 3,
                    imageRes = com.booktrails.ui_module.R.drawable.robinson_crusoe_image,
                    title = "Robinson Crusoe",
                    author = "Daniel Defoe",
                    pages = 260,
                    readStatus = ReadStatus.READING,
                    rating = 0
                ),
                Books(
                    id = 4,
                    imageRes = com.booktrails.ui_module.R.drawable.plague_image,
                    title = "Plague",
                    author = "Albert Camus",
                    pages = 200,
                    readStatus = ReadStatus.TO_READ,
                    rating = 0
                ),
                Books(
                    id = 1,
                    imageRes = com.booktrails.ui_module.R.drawable.potter_image,
                    title = "1984",
                    author = "George Orwell",
                    pages = 328,
                    readStatus = ReadStatus.READING,
                    rating = 4
                ),
                Books(
                    id = 2,
                    imageRes = com.booktrails.ui_module.R.drawable.ulysses_image,
                    title = "Ulysses",
                    author = "James Joyce",
                    pages = 1200,
                    readStatus = ReadStatus.READ,
                    rating = 4
                ),
                Books(
                    id = 3,
                    imageRes = com.booktrails.ui_module.R.drawable.robinson_crusoe_image,
                    title = "Robinson Crusoe",
                    author = "Daniel Defoe",
                    pages = 260,
                    readStatus = ReadStatus.READING,
                    rating = 0
                ),
                Books(
                    id = 4,
                    imageRes = com.booktrails.ui_module.R.drawable.plague_image,
                    title = "Plague",
                    author = "Albert Camus",
                    pages = 200,
                    readStatus = ReadStatus.TO_READ,
                    rating = 0
                )
            )
        }
    }

}


