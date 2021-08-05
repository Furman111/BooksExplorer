package ru.furman.booksexplorer.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collect
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.books.BooksUiEffect
import ru.furman.booksexplorer.model.ui.books.BooksUiEvent
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.ui.Screens
import ru.furman.booksexplorer.ui.common.CommonError
import ru.furman.booksexplorer.ui.common.Toolbar
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.books.BooksViewModel
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: BooksViewModel) {
    StatesOf(viewModel) { state, effect ->
        val carouselScrollState = rememberLazyListState()

        LaunchedEffect(effect) {
            effect.collect { effect ->
                if (effect is BooksUiEffect.NavigateToDetails) {
                    navController.navigate(Screens.DETAILS.name)
                    navController.currentBackStackEntry?.arguments =
                        bundleOf(BookDetailsViewModel.ARG_BOOK to effect.book)
                }
            }
        }

        BooksExplorerTheme {
            Surface {
                Column(Modifier.fillMaxSize()) {
                    Toolbar(title = stringResource(id = R.string.main_toolbar_title))
                    SwipeRefresh(
                        modifier = Modifier.fillMaxSize(),
                        state = rememberSwipeRefreshState(state is BooksUiState.InProgress),
                        onRefresh = {
                            viewModel.handleEvent(BooksUiEvent.SwipeToRefresh)
                        }
                    ) {
                        if (state is BooksUiState.Error) {
                            CommonError(
                                Modifier
                                    .verticalScroll(rememberScrollState())
                                    .fillMaxSize()
                            )
                        } else {
                            LazyColumn(
                                state = rememberLazyListState(),
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                val onBookClick = { book: Book ->
                                    viewModel.handleEvent(
                                        BooksUiEvent.BookClick(book)
                                    )
                                }
                                when (state) {
                                    is BooksUiState.Idle -> {
                                        lazyListContent(
                                            carouselBooks = state.carouselBooks,
                                            listBooks = state.listBooks,
                                            carouselScrollState = carouselScrollState,
                                            onClick = onBookClick
                                        )
                                    }
                                    is BooksUiState.InProgress -> {
                                        lazyListContent(
                                            carouselBooks = state.carouselBooks,
                                            listBooks = state.listBooks,
                                            carouselScrollState = carouselScrollState,
                                            onClick = onBookClick
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

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.lazyListContent(
    carouselBooks: List<Book>,
    listBooks: List<Book>,
    carouselScrollState: LazyListState,
    onClick: (book: Book) -> Unit
) {
    if (carouselBooks.isNotEmpty()) {
        stickyHeader("bestsellers_header") {
            Header(textRes = R.string.main_carousel_title)
        }
        item(key = "bestsellers_carousel") {
            BooksCarousel(
                carouselBooks,
                carouselScrollState,
                onClick
            )
        }
    }
    if (listBooks.isNotEmpty()) {
        stickyHeader("all_books_header") {
            Header(textRes = R.string.main_list_title)
        }
        items(listBooks, key = Book::toString) { book ->
            BookListItem(book, onClick)
        }
    }
}

@Composable
private fun Header(@StringRes textRes: Int) {
    Surface(color = MaterialTheme.colors.primarySurface.copy(alpha = ContentAlpha.medium)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = LocalContext.current.getString(textRes),
            style = MaterialTheme.typography.subtitle1.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
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
        items(books, key = Book::toString) { book ->
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