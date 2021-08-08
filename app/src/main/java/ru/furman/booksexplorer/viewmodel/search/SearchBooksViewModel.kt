package ru.furman.booksexplorer.viewmodel.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.furman.booksexplorer.data.repository.BooksRepository
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.common.EmptyUiEffect
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiEvent
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiState
import ru.furman.booksexplorer.utils.coroutine.launchSingle
import ru.furman.booksexplorer.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : BaseViewModel<BooksSearchUiState, BooksSearchUiEvent, EmptyUiEffect>() {

    private var loadJob: Job? = null

    private val requestsFlow = MutableStateFlow("")

    private val currentBooks: List<Book>
        get() = (currentState as? BooksSearchUiState.Stable)?.books ?: emptyList()

    init {
        collectRequests()
    }

    override fun handleEvent(event: BooksSearchUiEvent) {
        when (event) {
            is BooksSearchUiEvent.NewSearchRequest -> {
                setState(
                    BooksSearchUiState.Stable(
                        event.request,
                        currentBooks,
                        false
                    )
                )
                loadJob?.cancel()
                viewModelScope.launch {
                    requestsFlow.emit(event.request)
                }
            }
        }
    }

    private fun collectRequests() {
        viewModelScope.launch {
            requestsFlow.debounce(500L)
                .collect { request ->
                    if (request.isNotBlank()) {
                        searchBooks(request)
                    } else {
                        withContext(Dispatchers.Main) {
                            setState(
                                BooksSearchUiState.Stable(
                                    request,
                                    emptyList(),
                                    false
                                )
                            )
                        }
                    }
                }
        }
    }

    private suspend fun searchBooks(request: String) {
        withContext(Dispatchers.Main) {
            setState(
                BooksSearchUiState.Stable(
                    request,
                    currentBooks,
                    true
                )
            )
        }
        viewModelScope.launchSingle(::loadJob, Dispatchers.IO) {
            try {
                val books = booksRepository.searchBooks(request)
                withContext(Dispatchers.Main) {
                    setState(
                        BooksSearchUiState.Stable(
                            request,
                            books,
                            false
                        )
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setState(BooksSearchUiState.Error(request))
                }
            }
        }
    }

    override fun createInitialState() =
        BooksSearchUiState.Stable("", emptyList(), false)

}