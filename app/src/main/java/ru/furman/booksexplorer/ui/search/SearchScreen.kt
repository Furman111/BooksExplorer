package ru.furman.booksexplorer.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiEvent
import ru.furman.booksexplorer.model.ui.search.BooksSearchUiState
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.search.SearchBooksViewModel

@Composable
fun SearchScreen(viewModel: SearchBooksViewModel) {
    StatesOf(viewModel = viewModel) { state, _ ->
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                SearchInput(state) { viewModel.handleEvent(BooksSearchUiEvent.NewSearchRequest(it)) }
            }
        }

    }
}

@Composable
private fun SearchInput(
    state: BooksSearchUiState,
    onValueChanged: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = state.searchRequest,
        onValueChange = onValueChanged,
        placeholder = { Text(text = "Enter request") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (state is BooksSearchUiState.Stable && state.isLoading) {
                Icon(Icons.Filled.Refresh, contentDescription = null)
            }
        },
        isError = state is BooksSearchUiState.Error,
        singleLine = true
    )
}