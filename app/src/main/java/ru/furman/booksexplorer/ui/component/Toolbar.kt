package ru.furman.booksexplorer.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme

@Composable
fun Toolbar(
    title: String,
    showBackIcon: Boolean = false,
    onBackIconClicked: (() -> Unit)? = null
) {
    TopAppBar(
        elevation = 4.dp,
    ) {
        if (showBackIcon) {
            IconButton(
                onClick = { onBackIconClicked?.invoke() },
                content = { Icon(Icons.Filled.ArrowBack, contentDescription = null) }
            )
        } else {
            Spacer(modifier = Modifier.width(16.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview
@Composable
fun ToolbarPreview() {
    BooksExplorerTheme {
        Toolbar(title = "For instance", showBackIcon = false)
    }
}