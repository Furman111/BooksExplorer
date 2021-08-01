package ru.furman.booksexplorer.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.model.ui.books.BooksUiState
import ru.furman.booksexplorer.ui.common.CommonError
import ru.furman.booksexplorer.ui.common.CommonProgress
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.utils.StatesOf
import ru.furman.booksexplorer.viewmodel.books.BooksViewModel

@Composable
fun MainScreen(viewModel: BooksViewModel) {
    StatesOf(viewModel) { state, effect ->
        Surface {
            Column {
                TopAppBar(
                    elevation = 4.dp
                ) {
                    Text(
                        text = stringResource(id = R.string.main_toolbar_title),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                when (state) {
                    is BooksUiState.Idle -> {
                        BooksCarousel(books = state.carouselBooks, onClick = {})
                    }
                    BooksUiState.InProgress -> CommonProgress()
                    BooksUiState.Error -> CommonError()
                }
            }
        }
    }
}

@Composable
fun BooksCarousel(books: List<Book>, onClick: (book: Book) -> Unit) {
    LazyRow(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
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
            onClick = {
                //todo
            }
        )
    }
}