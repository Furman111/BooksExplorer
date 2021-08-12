package ru.furman.booksexplorer.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme

@Composable
fun Toolbar(
    title: String,
    subtitle: String? = null,
    showBackIcon: Boolean = false,
    onBackIconClicked: (() -> Unit)? = null
) {
    Surface(
        Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.primarySurface
    ) {
        Row(
            modifier = Modifier.defaultMinSize(minHeight = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackIcon) {
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    onClick = { onBackIconClicked?.invoke() },
                    content = { Icon(Icons.Filled.ArrowBack, contentDescription = null) }
                )
                Spacer(modifier = Modifier.width(8.dp))
            } else {
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = subtitle,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ToolbarPreview() {
    BooksExplorerTheme {
        Toolbar(title = "For instance", subtitle = "Some subtitle", showBackIcon = true)
    }
}