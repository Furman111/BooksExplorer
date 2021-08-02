package ru.furman.booksexplorer.model.ui.details

import ru.furman.booksexplorer.model.ui.UiState

data class BookDetailsUiState(
    val toolbarTitle: String,
    val firstPage: FistPage,
    val secondPage: SecondPage,
    val isFirstPageSelected: Boolean
) : UiState {

    data class FistPage(
        val title: String,
        val author: String,
        val genre: String,
        val description: String,
        val imageUrl: String
    )

    data class SecondPage(
        val isbn: String,
        val publishedDate: String,
        val publisher: String
    )

}
