package ru.furman.booksexplorer.model.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val title: String,
    val author: String,
    val genre: String,
    val description: String,
    val isbn: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("published")
    val publishedDate: String,
    val publisher: String
) : Parcelable