package ru.furman.booksexplorer.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.flow.collect
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiEvent
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiState
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

                val onPageSelected = { page: Int ->
                    viewModel.handleEvent(BookDetailsUiEvent.PageSelected(page))
                }

                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect { onPageSelected(it) }
                }
                Tabs(state = state, pagerState = pagerState, onPageSelected = onPageSelected)
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

@Composable
@ExperimentalPagerApi
private fun Tabs(state: BookDetailsUiState, pagerState: PagerState, onPageSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        Tab(
            text = { Text(stringResource(id = R.string.book_details_overview_tab)) },
            selected = state.isFirstPageSelected,
            onClick = {
                onPageSelected(0)
            },
        )
        Tab(
            text = { Text(stringResource(id = R.string.book_details_details_tab)) },
            selected = !state.isFirstPageSelected,
            onClick = { onPageSelected(1) },
        )
    }
}