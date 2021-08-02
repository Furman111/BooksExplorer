package ru.furman.booksexplorer.viewmodel.books

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.furman.booksexplorer.mapper.BooksMapper
import ru.furman.booksexplorer.model.ui.books.BooksUiEffect
import ru.furman.booksexplorer.model.ui.books.BooksUiEvent
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.repository.BooksRepository
import ru.furman.booksexplorer.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val booksMapper: BooksMapper
) : BaseViewModel<BooksUiState, BooksUiEvent, BooksUiEffect>() {

    init {
        loadBooks()
    }

    override fun handleEvent(event: BooksUiEvent) {
        when (event) {
            is BooksUiEvent.BookClick -> {
                setEffect(BooksUiEffect.NavigateToDetails(event.book))
            }
            BooksUiEvent.SwipeToRefresh -> {
                loadBooks()
            }
        }
    }

    private fun loadBooks() {
        setState(booksMapper.getProgressState(state.value))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(1500L)
                val state = booksMapper.getIdleState(booksRepository.getBooks())
                withContext(Dispatchers.Main) {
                    setState(state)
                }
            } catch (e: Exception) {
                Log.e("BooksViewModel", e.toString())
                withContext(Dispatchers.Main) {
                    setState(BooksUiState.Error)
                }
            }
        }
    }

    override fun createInitialState(): BooksUiState {
        return booksMapper.getProgressState()
    }

}