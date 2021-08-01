package ru.furman.booksexplorer.mapper

import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import javax.inject.Inject

class BooksMapper @Inject constructor() {

    fun getIdleState(loadedBooks: List<Book>): BooksUiState.Idle {
        return BooksUiState.Idle(
            carouselBooks = loadedBooks.take(CAROUSEL_BOOKS_COUNT),
            listBooks = loadedBooks.drop(CAROUSEL_BOOKS_COUNT)
        )
    }

    fun getProgressState(currentState: BooksUiState? = null): BooksUiState.InProgress {
        return BooksUiState.InProgress(
            carouselBooks = (currentState as? BooksUiState.Idle)?.carouselBooks ?: emptyList(),
            listBooks = (currentState as? BooksUiState.Idle)?.listBooks ?: emptyList(),
        )
    }

    companion object {

        private const val CAROUSEL_BOOKS_COUNT = 5

    }

}