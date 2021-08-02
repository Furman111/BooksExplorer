package ru.furman.booksexplorer.viewmodel.details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.furman.booksexplorer.model.ui.books.BooksUiEffect
import ru.furman.booksexplorer.model.ui.books.BooksUiEvent
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BooksUiState, BooksUiEvent, BooksUiEffect>() {

    override fun handleEvent(event: BooksUiEvent) {
    }

    override fun createInitialState(): BooksUiState {
        return BooksUiState.Error
    }

    companion object {

        const val ARG_BOOK = "book"

    }

}