package ru.furman.booksexplorer.ui.details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.ui.details.BookDetailsUiState

@Composable
fun DetailsSecondPageScreen(
    modifier: Modifier = Modifier,
    secondPage: BookDetailsUiState.SecondPage
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            DetailsItem(
                keyRes = R.string.book_details_isbn,
                value = secondPage.isbn
            )
            Spacer(modifier = Modifier.height(8.dp))
            DetailsItem(
                keyRes = R.string.book_details_published_date,
                value = secondPage.publishedDate
            )
            Spacer(modifier = Modifier.height(8.dp))
            DetailsItem(
                keyRes = R.string.book_details_publisher,
                value = secondPage.publisher
            )
        }
    }
}

@Composable
fun DetailsItem(@StringRes keyRes: Int, value: String) {
    val text = buildAnnotatedString {
        append(LocalContext.current.getString(keyRes))
        append(' ')
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(value)
        }
    }
    Text(
        text = text,
        style = MaterialTheme.typography.body1
    )
}