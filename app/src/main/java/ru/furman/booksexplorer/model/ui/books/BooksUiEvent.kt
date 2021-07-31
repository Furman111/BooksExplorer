package ru.furman.booksexplorer.model.ui.books

import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.UiEvent

sealed class BooksUiEvent : UiEvent {

    object SwipeToRefresh : BooksUiEvent()

    data class BookClick(val book: Book) : BooksUiEvent()

}