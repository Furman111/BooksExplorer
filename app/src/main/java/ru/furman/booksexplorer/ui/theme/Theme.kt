package ru.furman.booksexplorer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.*

@Composable
fun BooksExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val dimensions = defaultDimensions

    ProvideDimensions(dimensions = dimensions) {
        MaterialTheme(
            colors = colors,
            typography = Typography(defaultFontFamily = Stix),
            shapes = Shapes,
            content = content
        )
    }
}

@Composable
fun ProvideDimensions(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalDimensions provides dimensionSet, content = content)
}

private val LocalDimensions = staticCompositionLocalOf {
    defaultDimensions
}

val MaterialTheme.dimensions: Dimensions
    @Composable get() = LocalDimensions.current