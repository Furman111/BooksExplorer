package ru.furman.booksexplorer.viewmodel.books

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.furman.booksexplorer.model.ui.books.BooksUiEvent
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.model.ui.common.EmptyUiEffect
import ru.furman.booksexplorer.repository.BooksRepository
import ru.furman.booksexplorer.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : BaseViewModel<BooksUiState, BooksUiEvent, EmptyUiEffect>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        setState(BooksUiState.Error)
    }

    init {
        loadBooks()
    }

    override fun handleEvent(event: BooksUiEvent) {
        when (event) {
            is BooksUiEvent.BookClick -> {
                //todo navigate to book details
            }
            BooksUiEvent.SwipeToRefresh -> {
                loadBooks()
            }
        }
    }

    private fun loadBooks() {
        setState(BooksUiState.InProgress)
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val books = booksRepository.getBooks()
            val state = BooksUiState.InProgress
            withContext(Dispatchers.Main) {
                setState(state)
            }
        }
    }

    override fun createInitialState(): BooksUiState {
        return BooksUiState.InProgress
    }

}