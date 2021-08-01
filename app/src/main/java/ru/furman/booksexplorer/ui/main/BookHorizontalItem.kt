package ru.furman.booksexplorer.ui.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme

@Composable
fun BookHorizontalItem(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: (Book) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick(book) }
    ) {
        Row(Modifier.padding(8.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = book.imageUrl,
                    builder = {
                        memoryCacheKey(book.toString())
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun BookHorizontalItemPreview() {
    val context = LocalContext.current
    BooksExplorerTheme {
        BookHorizontalItem(
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
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Toast.makeText(context, "Clicked :)", Toast.LENGTH_SHORT).show()
        }
    }
}