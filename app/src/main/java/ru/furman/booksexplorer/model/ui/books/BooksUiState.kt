package ru.furman.booksexplorer.model.ui.books

import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.UiState

sealed class BooksUiState : UiState {

    object InProgress : BooksUiState()

    data class Idle(
        val carouselBooks: List<Book>,
        val listBooks: List<Book>
    ) : BooksUiState()

    object Error : BooksUiState()

}