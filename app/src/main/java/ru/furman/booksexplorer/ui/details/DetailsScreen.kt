package ru.furman.booksexplorer.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiEffect
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiEvent
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiState
import ru.furman.booksexplorer.ui.component.Toolbar
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.utils.CollectEffects
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

@Composable
@ExperimentalPagerApi
fun DetailsScreen(viewModel: BookDetailsViewModel, navigateBack: () -> Unit) {
    StatesOf(viewModel = viewModel) { state, effects ->
        CollectEffects(effects) { effect ->
            if (effect == BookDetailsUiEffect.NavigateBack) {
                navigateBack()
            }
        }

        Content(
            state = state,
            onPageSelected = { page ->
                viewModel.handleEvent(BookDetailsUiEvent.PageSelected(page))
            },
            onBackButtonClick = {
                viewModel.handleEvent(BookDetailsUiEvent.OnBackPressed)
            }
        )
    }
}

@Composable
@ExperimentalPagerApi
private fun Content(
    state: BookDetailsUiState,
    onPageSelected: (Int) -> Unit,
    onBackButtonClick: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Toolbar(
            title = state.toolbarTitle,
            showBackIcon = true,
            onBackIconClicked = onBackButtonClick
        )
        val pagerState = rememberPagerState(pageCount = 2)

        rememberCoroutineScope().launch {
            pagerState.animateScrollToPage(if (state.isFirstPageSelected) 0 else 1)
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

@Preview
@Composable
@ExperimentalPagerApi
private fun DetailsScreenPreview() {
    Surface {
        BooksExplorerTheme {
            Content(
                state = BookDetailsUiState(
                    toolbarTitle = "Book's name",
                    firstPage = BookDetailsUiState.FistPage(
                        title = "Evgeniy Onegin",
                        author = "Pushkin",
                        genre = "Roman",
                        description = "Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool",
                        imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.labirint.ru%2Fbooks%2F669673%2F&psig=AOvVaw2Lx0h1Dmze0XREl4AtvWWu&ust=1627889904166000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCLDo9M6oj_ICFQAAAAAdAAAAABAD",
                    ),
                    secondPage = BookDetailsUiState.SecondPage(
                        isbn = "21412523534543",
                        publishedDate = "28.06.1996",
                        publisher = "Piter"
                    ),
                    isFirstPageSelected = true
                ),
                onPageSelected = {},
                onBackButtonClick = {}
            )
        }
    }
}