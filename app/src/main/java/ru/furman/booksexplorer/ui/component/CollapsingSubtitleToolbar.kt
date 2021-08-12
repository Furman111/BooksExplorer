package ru.furman.booksexplorer.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.max
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme

@Composable
fun CollapsingSubtitleToolbar(
    title: String,
    subtitle: String? = null,
    showBackIcon: Boolean = false,
    scrollOffset: Float = 1f,
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
                BackIcon(onBackIconClicked)
            } else {
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp)) {
                ToolbarTitle(title)
                subtitle?.let { ToolbarSubtitle(subtitle, scrollOffset) }
            }
        }
    }
}

@Composable
private fun ToolbarTitle(title: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
    )
}

@Composable
private fun BackIcon(onBackIconClicked: (() -> Unit)?) {
    Spacer(modifier = Modifier.width(4.dp))
    IconButton(
        onClick = { onBackIconClicked?.invoke() },
        content = { Icon(Icons.Filled.ArrowBack, contentDescription = null) }
    )
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
private fun ToolbarSubtitle(subtitle: String, scrollOffset: Float) {
    var subtitleMaxHeight by remember(subtitle) { mutableStateOf(Dp.Unspecified) }
    val density = LocalDensity.current.density

    val height by animateDpAsState(max(0.dp, subtitleMaxHeight * scrollOffset))
    Box(
        Modifier
            .fillMaxWidth()
            .onSizeChanged { size ->
                val newHeight = (size.height.toFloat() / density).toInt().dp
                if (newHeight > subtitleMaxHeight || subtitleMaxHeight.isUnspecified) {
                    subtitleMaxHeight = newHeight
                }
            }
            .height(height)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = subtitle,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview
@Composable
fun ToolbarPreview() {
    BooksExplorerTheme {
        CollapsingSubtitleToolbar(
            title = "For instance",
            subtitle = "Some subtitle",
            showBackIcon = true
        )
    }
}