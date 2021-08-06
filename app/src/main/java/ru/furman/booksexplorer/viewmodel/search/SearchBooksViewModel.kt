package ru.furman.booksexplorer.viewmodel.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import ru.furman.booksexplorer.data.repository.BooksRepository
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.common.EmptyUiEffect
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiEvent
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiState
import ru.furman.booksexplorer.utils.subscribe.LaunchUtils
import ru.furman.booksexplorer.utils.subscribe.LaunchUtilsImpl
import ru.furman.booksexplorer.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : BaseViewModel<BooksSearchUiState, BooksSearchUiEvent, EmptyUiEffect>(),
    LaunchUtils by LaunchUtilsImpl() {

    private var loadJob: Job? = null

    private val currentBooks: List<Book>
        get() = (currentState as? BooksSearchUiState.Stable)?.books ?: emptyList()

    override fun handleEvent(event: BooksSearchUiEvent) {
        when (event) {
            is BooksSearchUiEvent.NewSearchRequest -> searchBooks(event)
        }
    }

    private fun searchBooks(request: BooksSearchUiEvent.NewSearchRequest) {
        setState(
            BooksSearchUiState.Stable(
                request.request,
                currentBooks,
                true
            )
        )
        viewModelScope.launchSingle(::loadJob) {
            try {
                val books = booksRepository.searchBooks(request.request)
                withContext(Dispatchers.Main) {
                    setState(
                        BooksSearchUiState.Stable(
                            request.request,
                            books,
                            false
                        )
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setState(BooksSearchUiState.Error(request.request))
                }
            }
        }
    }

    override fun createInitialState() =
        BooksSearchUiState.Stable("", emptyList(), false)

}