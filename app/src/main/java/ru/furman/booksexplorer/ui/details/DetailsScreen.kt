package ru.furman.booksexplorer.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
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
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                    verticalAlignment = Alignment.Top
                ) { page ->
                    if (page == 0) {
                        DetailsFirstPageScreen(Modifier.padding(16.dp), state.firstPage)
                    } else {
                        DetailsSecondPageScreen(
                            Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            state.secondPage
                        )
                    }
                }
            }
        }
    }
}