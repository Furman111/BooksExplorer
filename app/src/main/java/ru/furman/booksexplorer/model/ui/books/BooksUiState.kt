package ru.furman.booksexplorer.model.ui.books

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.UiState

sealed class BooksUiState : UiState {

    data class Stable(
        val carouselBooks: List<Book>,
        val booksFlow: Flow<PagingData<Book>>,
        val isUpdating: Boolean
    ) : BooksUiState()

    object Error : BooksUiState()

}