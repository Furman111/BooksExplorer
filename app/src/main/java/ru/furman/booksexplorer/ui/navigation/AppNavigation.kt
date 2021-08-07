package ru.furman.booksexplorer.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.ui.details.DetailsScreen
import ru.furman.booksexplorer.ui.main.MainScreen
import ru.furman.booksexplorer.ui.search.SearchScreen
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

internal sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @StringRes val labelRes: Int
) {
    object Main : Screen("mainroot", Icons.Filled.Home, R.string.main_label)
    object Search : Screen("searchroot", Icons.Filled.Search, R.string.search_label)
}

private val bottomNavigationScreens: List<Screen> =
    listOf(Screen.Main, Screen.Search)

private sealed class LeafScreen(val route: String, val showBottomNavigation: Boolean = true) {
    object Main : LeafScreen("main")
    object Search : LeafScreen("search")
    object Details : LeafScreen("details", false)

    companion object {

        private val allLeafScreens = listOf(Main, Search, Details)

        fun screenByRoute(route: String): LeafScreen? {
            return allLeafScreens.find { screen -> screen.route == route }
        }

    }

}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
internal fun AppNavigation(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            if (LeafScreen.screenByRoute(
                    currentDestination?.route ?: ""
                )?.showBottomNavigation != false
            ) {
                BottomNavigation {
                    BottomNavigationItems(currentDestination, navController)
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            addMainTopLevel(navController)
            addSearchTopLevel(navController)
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItems(
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    bottomNavigationScreens.forEach { screen ->
        BottomNavigationItem(
            modifier = Modifier.background(MaterialTheme.colors.surface),
            icon = { Icon(screen.icon, contentDescription = null) },
            label = {
                Text(
                    text = stringResource(screen.labelRes),
                    fontWeight = FontWeight.Bold
                )
            },
            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
            onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            selectedContentColor = MaterialTheme.colors.onSurface
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
private fun NavGraphBuilder.addMainTopLevel(navController: NavController) {
    navigation(
        route = Screen.Main.route,
        startDestination = LeafScreen.Main.route
    ) {
        addMain(navController)
        addDetails(navController)
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
private fun NavGraphBuilder.addSearchTopLevel(navController: NavController) {
    navigation(
        route = Screen.Search.route,
        startDestination = LeafScreen.Search.route
    ) {
        addSearch(navController)
        addDetails(navController)
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.addMain(navController: NavController) {
    composable(LeafScreen.Main.route) {
        MainScreen(hiltViewModel()) { book ->
            navController.navigate(LeafScreen.Details.route)
            navController.currentBackStackEntry?.arguments =
                bundleOf(BookDetailsViewModel.ARG_BOOK to book)
        }
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addSearch(navController: NavController) {
    composable(LeafScreen.Search.route) {
        SearchScreen(hiltViewModel())
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addDetails(navController: NavController) {
    composable(
        LeafScreen.Details.route,
        arguments = listOf(navArgument(BookDetailsViewModel.ARG_BOOK) {
            type = NavType.ParcelableType(Book::class.java)
        })
    ) {
        DetailsScreen(viewModel = hiltViewModel()) {
            navController.popBackStack()
        }
    }
}