package ru.furman.booksexplorer.viewmodel.details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.common.EmptyUiEffect
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiEvent
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiState
import ru.furman.booksexplorer.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BookDetailsUiState, BookDetailsUiEvent, EmptyUiEffect>() {

    private val book: Book = savedStateHandle.get<Book>(ARG_BOOK)
        ?: throw IllegalArgumentException("Book must be present")

    override fun handleEvent(event: BookDetailsUiEvent) {
        when (event) {
            BookDetailsUiEvent.OnBackPressed -> {
                //todo
            }
            is BookDetailsUiEvent.PageSelected -> {
                setState(currentState.copy(isFirstPageSelected = event.page == 0))
            }
        }
    }

    override fun createInitialState(): BookDetailsUiState {
        return BookDetailsUiState(
            toolbarTitle = book.title,
            firstPage = BookDetailsUiState.FistPage(
                title = book.title,
                author = book.author,
                genre = book.genre,
                description = book.description,
                imageUrl = book.imageUrl
            ),
            secondPage = BookDetailsUiState.SecondPage(
                isbn = book.isbn,
                publishedDate = book.publishedDate,
                publisher = book.publisher
            ),
            isFirstPageSelected = true
        )
    }

    companion object {

        const val ARG_BOOK = "book"

    }

}