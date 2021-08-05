package ru.furman.booksexplorer.model.ui.books

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.UiState

sealed class BooksUiState : UiState {

    abstract val booksFlow: Flow<PagingData<Book>>

    data class Stable(
        val carouselBooks: List<Book>,
        override val booksFlow: Flow<PagingData<Book>>,
        val isUpdating: Boolean
    ) : BooksUiState()

    data class Error(override val booksFlow: Flow<PagingData<Book>>) : BooksUiState()

}