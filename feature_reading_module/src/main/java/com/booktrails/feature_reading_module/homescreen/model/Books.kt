package com.booktrails.feature_reading_module.homescreen.model

data class Books (
    val id: Int,
    val imageRes: Int,
    val title: String,
    val author: String,
    val pages: Int,
    val readStatus: ReadStatus,
    val rating: Int
)