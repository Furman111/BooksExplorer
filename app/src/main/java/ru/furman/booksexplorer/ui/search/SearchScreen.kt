package ru.furman.booksexplorer.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiEvent
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiState
import ru.furman.booksexplorer.ui.component.CommonError
import ru.furman.booksexplorer.ui.main.BookListItem
import ru.furman.booksexplorer.ui.theme.dimensions
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.search.SearchBooksViewModel

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    viewModel: SearchBooksViewModel,
    navigateToDetails: (Book) -> Unit
) {
    StatesOf(viewModel = viewModel) { state, _ ->
        val lazyListState = rememberLazyListState()

        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                SearchInput(state) { viewModel.handleEvent(BooksSearchUiEvent.NewSearchRequest(it)) }
                when (state) {
                    is BooksSearchUiState.Stable -> SearchResultList(
                        lazyListState,
                        state,
                        navigateToDetails
                    )
                    is BooksSearchUiState.Error -> CommonError(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SearchResultList(
    lazyListState: LazyListState,
    state: BooksSearchUiState.Stable,
    onClick: (Book) -> Unit
) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = MaterialTheme.dimensions.halfPadding,
            bottom = MaterialTheme.dimensions.halfPadding
        )
    ) {
        itemsIndexed(
            items = state.books,
            key = { _, book -> book.toString() }
        ) { index, book ->
            BookListItem(
                book = book,
                onClick = onClick,
                drawDivider = index != state.books.lastIndex
            )
        }
    }
}

@Composable
private fun SearchInput(
    state: BooksSearchUiState,
    onValueChanged: (String) -> Unit
) {
    val textInputService = LocalTextInputService.current
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.dimensions.halfPadding,
                top = MaterialTheme.dimensions.halfPadding,
                end = MaterialTheme.dimensions.halfPadding,
                bottom = 0.dp
            )
            .clipToBounds(),
        value = state.searchRequest,
        onValueChange = onValueChanged,
        placeholder = { Text(text = "Enter request") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (state is BooksSearchUiState.Stable && state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            textInputService?.hideSoftwareKeyboard()
            focusManager.clearFocus()
        },
        isError = state is BooksSearchUiState.Error,
        singleLine = true
    )
}