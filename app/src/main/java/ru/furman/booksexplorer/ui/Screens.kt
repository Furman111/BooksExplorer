package ru.furman.booksexplorer.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import ru.furman.booksexplorer.R

enum class Screens {
    MAIN,
    SEARCH,
    DETAILS;
}

sealed class BottomNavigationScreen(
    val screen: Screens, val icon: ImageVector, @StringRes val labelRes: Int
) {

    object Main : BottomNavigationScreen(
        screen = Screens.MAIN,
        icon = Icons.Filled.Home,
        labelRes = R.string.main_label
    )

    object Search : BottomNavigationScreen(
        screen = Screens.SEARCH,
        icon = Icons.Filled.Search,
        labelRes = R.string.search_label
    )

}