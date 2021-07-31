package ru.furman.booksexplorer.mapper

import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import javax.inject.Inject

class BooksMapper @Inject constructor() {

    fun getState(loadedBooks: List<Book>): BooksUiState.Idle {
        return BooksUiState.Idle(
            carouselBooks = loadedBooks.take(CAROUSEL_BOOKS_COUNT),
            listBooks = loadedBooks.drop(CAROUSEL_BOOKS_COUNT)
        )
    }

    companion object {

        private const val CAROUSEL_BOOKS_COUNT = 5

    }

}