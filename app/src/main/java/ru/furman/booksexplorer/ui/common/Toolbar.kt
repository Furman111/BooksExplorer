package ru.furman.booksexplorer.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme

@Composable
fun Toolbar(title: String) {
    TopAppBar(
        elevation = 4.dp
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun ToolbarPreview() {
    BooksExplorerTheme {
        Toolbar(title = "For instance")
    }
}