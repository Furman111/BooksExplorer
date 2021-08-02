package ru.furman.booksexplorer.model.ui.books

import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.UiEffect

sealed class BooksUiEffect : UiEffect {

    data class NavigateToDetails(val book: Book) : BooksUiEffect()

}