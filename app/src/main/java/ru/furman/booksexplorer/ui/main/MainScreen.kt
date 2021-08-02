package ru.furman.booksexplorer.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
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
                        if (state is BooksUiState.Error) {
                            CommonError()
                        } else {
                            LazyColumn(
                                state = rememberLazyListState(),
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                when (state) {
                                    is BooksUiState.Idle -> {
                                        lazyListContent(
                                            carouselBooks = state.carouselBooks,
                                            listBooks = state.listBooks,
                                            carouselScrollState = carouselScrollState,
                                            onClick = {}
                                        )
                                    }
                                    is BooksUiState.InProgress -> {
                                        lazyListContent(
                                            carouselBooks = state.carouselBooks,
                                            listBooks = state.listBooks,
                                            carouselScrollState = carouselScrollState,
                                            onClick = {}
                                        )
                                    }
                                    BooksUiState.Error -> Unit
                                }
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

private fun LazyListScope.lazyListContent(
    carouselBooks: List<Book>,
    listBooks: List<Book>,
    carouselScrollState: LazyListState,
    onClick: (book: Book) -> Unit
) {
    item {
        BooksCarousel(
            carouselBooks,
            carouselScrollState,
            {})
    }
    items(listBooks) { book ->
        BookListItem(book, onClick)
    }
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

@Composable
private fun BookListItem(book: Book, onClick: (book: Book) -> Unit) {
    BookVerticalItem(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        book = book,
        onClick = onClick
    )
    Divider(Modifier.padding(start = 16.dp, end = 16.dp))
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
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun BooksListItemPreview() {
    BooksExplorerTheme {
        BookListItem(
            book = Book(
                title = "Evgeniy Onegin",
                author = "Pushkin",
                genre = "Roman",
                description = "Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool",
                isbn = "123412535",
                imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.labirint.ru%2Fbooks%2F669673%2F&psig=AOvVaw2Lx0h1Dmze0XREl4AtvWWu&ust=1627889904166000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCLDo9M6oj_ICFQAAAAAdAAAAABAD",
                publishedDate = "213213",
                publisher = "Piter"
            ),
            onClick = {}
        )
    }
}