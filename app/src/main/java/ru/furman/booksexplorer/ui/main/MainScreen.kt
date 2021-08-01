package ru.furman.booksexplorer.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.books.BooksUiEvent
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.ui.common.CommonError
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.books.BooksViewModel

@Composable
fun MainScreen(viewModel: BooksViewModel) {
    StatesOf(viewModel) { state, _ ->
        val carouselScrollState = rememberLazyListState()

        BooksExplorerTheme {
            Surface {
                Column(Modifier.fillMaxSize()) {
                    Toolbar()
                    SwipeRefresh(
                        modifier = Modifier.fillMaxSize(),
                        state = rememberSwipeRefreshState(state is BooksUiState.InProgress),
                        onRefresh = {
                            viewModel.handleEvent(BooksUiEvent.SwipeToRefresh)
                        }
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            when (state) {
                                is BooksUiState.Idle -> {
                                    Content(
                                        carouselBooks = state.carouselBooks,
                                        listBooks = state.listBooks,
                                        carouselScrollState = carouselScrollState,
                                        onClick = {}
                                    )
                                }
                                is BooksUiState.InProgress -> {
                                    Content(
                                        carouselBooks = state.carouselBooks,
                                        listBooks = state.listBooks,
                                        carouselScrollState = carouselScrollState,
                                        onClick = {}
                                    )
                                }
                                BooksUiState.Error -> CommonError()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Toolbar() {
    TopAppBar(
        elevation = 4.dp
    ) {
        Text(
            text = stringResource(id = R.string.main_toolbar_title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun Content(
    carouselBooks: List<Book>,
    listBooks: List<Book>,
    carouselScrollState: LazyListState,
    onClick: (book: Book) -> Unit
) {
    BooksCarousel(carouselBooks, carouselScrollState, onClick)
}

@Composable
private fun BooksCarousel(
    books: List<Book>,
    scrollState: LazyListState,
    onClick: (book: Book) -> Unit
) {
    LazyRow(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        state = scrollState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { book ->
            BookHorizontalItem(
                modifier = Modifier.width(250.dp),
                book = book,
                onClick = onClick
            )
        }
    }
}

@Preview
@Composable
private fun BooksCarouselPreview() {
    BooksExplorerTheme {
        BooksCarousel(
            books = List(5) {
                Book(
                    title = "Evgeniy Onegin $it",
                    author = "Pushkin",
                    genre = "Roman",
                    description = "Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool",
                    isbn = "123412535",
                    imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.labirint.ru%2Fbooks%2F669673%2F&psig=AOvVaw2Lx0h1Dmze0XREl4AtvWWu&ust=1627889904166000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCLDo9M6oj_ICFQAAAAAdAAAAABAD",
                    publishedDate = "213213",
                    publisher = "Piter"
                )
            },
            scrollState = rememberLazyListState(),
            onClick = {
                //todo
            }
        )
    }
}