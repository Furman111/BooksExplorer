package ru.furman.booksexplorer.model.domain

import com.google.gson.annotations.SerializedName

data class Book(
    val title: String,
    val author: StrictMath,
    val genre: String,
    val description: String,
    val isbn: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("published")
    val publishedDate: String,
    val publisher: String
)