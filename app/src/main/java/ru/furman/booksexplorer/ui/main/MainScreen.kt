package ru.furman.booksexplorer.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.books.BooksUiEffect
import ru.furman.booksexplorer.model.ui.books.BooksUiEvent
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.ui.Screens
import ru.furman.booksexplorer.ui.component.CommonError
import ru.furman.booksexplorer.ui.component.Toolbar
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.utils.CollectEffects
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.books.BooksViewModel
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(navController: NavController, viewModel: BooksViewModel) {
    StatesOf(viewModel) { state, effects ->
        val carouselScrollState = rememberLazyListState()
        val lazyListState = rememberLazyListState()

        CollectEffects(effects) { effect ->
            when (effect) {
                is BooksUiEffect.NavigateToDetails -> {
                    navController.navigate(Screens.DETAILS.name)
                    navController.currentBackStackEntry?.arguments =
                        bundleOf(BookDetailsViewModel.ARG_BOOK to effect.book)
                }
            }
        }

        Surface {
            Column(Modifier.fillMaxSize()) {
                Toolbar(title = stringResource(id = R.string.main_toolbar_title))
                SwipeRefresh(
                    modifier = Modifier.fillMaxSize(),
                    state = rememberSwipeRefreshState(
                        (state as? BooksUiState.Stable)?.isUpdating ?: false
                    ),
                    onRefresh = {
                        viewModel.handleEvent(BooksUiEvent.SwipeToRefresh)
                    }
                ) {
                    val onBookClick = { book: Book ->
                        viewModel.handleEvent(
                            BooksUiEvent.BookClick(book)
                        )
                    }

                    when (state) {
                        BooksUiState.Error -> {
                            CommonError(
                                Modifier
                                    .verticalScroll(rememberScrollState())
                                    .fillMaxSize()
                            )
                        }
                        is BooksUiState.Stable -> {
                            val booksPagingItems = state.booksFlow.collectAsLazyPagingItems()
                            LazyColumn(
                                state = lazyListState,
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                lazyListContent(
                                    carouselBooks = state.carouselBooks,
                                    booksPagingItems = booksPagingItems,
                                    carouselScrollState = carouselScrollState,
                                    onClick = onBookClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
private fun LazyListScope.lazyListContent(
    carouselBooks: List<Book>,
    booksPagingItems: LazyPagingItems<Book>,
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

    if (booksPagingItems.itemCount > 0) {
        stickyHeader("all_books_header") {
            Header(textRes = R.string.main_list_title)
        }
    }
    itemsIndexed(booksPagingItems, key = { _, book -> book.toString() }) { index, book ->
        if (book != null) {
            BookListItem(book, onClick, index != booksPagingItems.itemCount - 1)
        }
    }

    if (booksPagingItems.loadState.append == LoadState.Loading) {
        item("append_load_progress") {
            Surface(Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    color = MaterialTheme.colors.onSurface
                )
            }
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

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@Composable
fun BookListItem(book: Book, onClick: (book: Book) -> Unit, drawDivider: Boolean) {
    BookVerticalItem(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        book = book,
        onClick = onClick
    )
    if (drawDivider) {
        Divider(Modifier.padding(start = 16.dp))
    }
}

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
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
            onClick = {},
            drawDivider = true
        )
    }
}