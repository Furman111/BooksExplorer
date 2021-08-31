package ru.furman.booksexplorer.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val padding: Dp = 16.dp,
    val halfPadding: Dp = 8.dp,
    val smallPadding: Dp = 4.dp,
    val toolbarMinHeight: Dp = 48.dp
)

val defaultDimensions = Dimensions()