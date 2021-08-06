package ru.furman.booksexplorer.model.ui.search

import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.UiState

sealed class BooksSearchUiState : UiState {

    abstract val searchRequest: String

    data class Stable(
        override val searchRequest: String,
        val books: List<Book>,
        val isLoading: Boolean
    ) : BooksSearchUiState()

    data class Error(override val searchRequest: String) : BooksSearchUiState()

}