package ru.furman.booksexplorer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ru.furman.booksexplorer.R

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple500,
    secondary = Cyan200
)

private val LightColorPalette = lightColors(
    primary = Purple200,
    primaryVariant = Purple500,
    secondary = Cyan200,
    secondaryVariant = Cyan500,
    onSecondary = Text,
    onBackground = Text,
    onSurface = Text
)

private val Stix = FontFamily(
    Font(R.font.stix_bold, FontWeight.Bold),
    Font(R.font.stix_medium, FontWeight.Medium),
    Font(R.font.stix_regular, FontWeight.Normal),
    Font(R.font.stix_semibold, FontWeight.SemiBold)
)

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

    MaterialTheme(
        colors = colors,
        typography = Typography(defaultFontFamily = Stix),
        shapes = Shapes,
        content = content
    )
}