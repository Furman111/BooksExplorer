package ru.furman.booksexplorer.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
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
    val textInputService = LocalTextInputService.current
    val focusManager = LocalFocusManager.current

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