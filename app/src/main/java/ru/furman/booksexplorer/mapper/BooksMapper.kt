package ru.furman.booksexplorer.mapper

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import javax.inject.Inject

class BooksMapper @Inject constructor() {

    fun getIdleState(
        booksFlow: Flow<PagingData<Book>>,
        carouselBooks: List<Book>
    ): BooksUiState.Idle {
        return BooksUiState.Idle(
            carouselBooks = carouselBooks.take(CAROUSEL_BOOKS_COUNT),
            booksFlow = booksFlow
        )
    }

    fun getProgressState(
        booksFlow: Flow<PagingData<Book>>,
        currentState: BooksUiState? = null
    ): BooksUiState.InProgress {
        return BooksUiState.InProgress(
            carouselBooks = (currentState as? BooksUiState.Idle)?.carouselBooks ?: emptyList(),
            booksFlow = booksFlow
        )
    }

    companion object {

        private const val CAROUSEL_BOOKS_COUNT = 5

    }

}