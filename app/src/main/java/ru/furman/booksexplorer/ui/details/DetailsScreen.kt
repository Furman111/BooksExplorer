package ru.furman.booksexplorer.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import ru.furman.booksexplorer.ui.common.Toolbar
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

@Composable
@ExperimentalPagerApi
fun DetailsScreen(viewModel: BookDetailsViewModel) {
    StatesOf(viewModel = viewModel) { state, effect ->
        BooksExplorerTheme {
            Column(Modifier.fillMaxSize()) {
                Toolbar(title = state.toolbarTitle)
                val pagerState = rememberPagerState(pageCount = 2)

                /*HorizontalPager(state = pagerState) { page ->

                }*/
            }
        }
    }
}