package ru.furman.booksexplorer.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiState
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme

@Composable
fun FirstPageScreen(
    modifier: Modifier = Modifier,
    firstPage: BookDetailsUiState.FistPage,
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    painter = rememberImagePainter(firstPage.imageUrl) {
                        crossfade(true)
                    },
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = firstPage.title, style = MaterialTheme.typography.h4)
                    Text(text = firstPage.author, style = MaterialTheme.typography.subtitle1)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = firstPage.genre, style = MaterialTheme.typography.subtitle2)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = firstPage.description, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
@Preview
fun FirstPageScreenPreview() {
    BooksExplorerTheme {
        FirstPageScreen(
            Modifier.padding(16.dp),
            firstPage = BookDetailsUiState.FistPage(
                title = "Evgeniy Onegin",
                author = "Pushkin",
                genre = "Roman",
                description = "Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool Cool cool cool",
                imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.labirint.ru%2Fbooks%2F669673%2F&psig=AOvVaw2Lx0h1Dmze0XREl4AtvWWu&ust=1627889904166000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCLDo9M6oj_ICFQAAAAAdAAAAABAD",
            )
        )
    }
}