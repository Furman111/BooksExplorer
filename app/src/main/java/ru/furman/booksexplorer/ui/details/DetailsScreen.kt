package ru.furman.booksexplorer.ui.details

import androidx.compose.runtime.Composable
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

@Composable
fun DetailsScreen(viewModel: BookDetailsViewModel) {
    StatesOf(viewModel = viewModel) { state, effect ->

    }
}