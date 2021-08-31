package ru.furman.booksexplorer.viewmodel.books

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.furman.booksexplorer.data.paging.BooksPagingSource
import ru.furman.booksexplorer.data.repository.BooksRepository
import ru.furman.booksexplorer.mapper.BooksMapper
import ru.furman.booksexplorer.model.ui.books.BooksUiEvent
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.model.ui.common.EmptyUiEffect
import ru.furman.booksexplorer.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val booksMapper: BooksMapper
) : BaseViewModel<BooksUiState, BooksUiEvent, EmptyUiEffect>() {

    private val booksFlow = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 1,
            enablePlaceholders = false,
            initialLoadSize = 10
        )
    ) { BooksPagingSource(booksRepository).also(::booksPagingSource::set) }
        .flow
        .cachedIn(viewModelScope)

    private lateinit var booksPagingSource: BooksPagingSource

    init {
        loadBooks()
    }

    override fun handleEvent(event: BooksUiEvent) {
        when (event) {
            BooksUiEvent.SwipeToRefresh -> {
                booksPagingSource.invalidate()
                loadBooks()
            }
        }
    }

    private fun loadBooks() {
        setState(booksMapper.getProgressState(booksFlow, currentState))
        viewModelScope.launch {
            try {
                val state = booksMapper.getIdleState(booksFlow, booksRepository.loadBooks())
                setState(state)
            } catch (e: Exception) {
                Log.e("BooksViewModel", e.toString())
                setState(BooksUiState.Error)
            }
        }
    }

    override fun createInitialState(): BooksUiState {
        return booksMapper.getProgressState(booksFlow)
    }

}