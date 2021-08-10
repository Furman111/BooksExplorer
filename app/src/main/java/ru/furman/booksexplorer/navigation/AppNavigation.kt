package ru.furman.booksexplorer.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.furman.booksexplorer.R
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.ui.details.DetailsScreen
import ru.furman.booksexplorer.ui.main.MainScreen
import ru.furman.booksexplorer.ui.search.SearchScreen
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

interface Navigable {

    val route: String

}

sealed class Graph : Navigable {

    sealed class BottomNavigation(
        override val route: String,
        val icon: ImageVector,
        @StringRes val labelRes: Int
    ) : Graph() {
        object Main : BottomNavigation("mainroot", Icons.Filled.Home, R.string.main_label)
        object Search : BottomNavigation("searchroot", Icons.Filled.Search, R.string.search_label)
    }

    object Details : Graph() {

        override val route = "detailsgraph"

    }

}

sealed class Screen(override val route: String) : Navigable {

    object Main : Screen("main")

    object Search : Screen("search")

    object Details : Screen("details")

}

sealed class BottomSheet(override val route: String) : Navigable {

    object Buy : Screen("buy")

}

val bottomNavigationGraphs: List<Graph.BottomNavigation> =
    listOf(Graph.BottomNavigation.Main, Graph.BottomNavigation.Search)


val noBottomBarNavigableList = listOf(Screen.Details, BottomSheet.Buy)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Graph.BottomNavigation.Main.route,
        modifier = modifier
    ) {
        addMainTopLevel(navController)
        addSearchTopLevel(navController)
        addDetails(navController)
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
private fun NavGraphBuilder.addMainTopLevel(navController: NavController) {
    navigation(
        route = Graph.BottomNavigation.Main.route,
        startDestination = Screen.Main.route
    ) {
        addMain(navController)
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
private fun NavGraphBuilder.addSearchTopLevel(navController: NavController) {
    navigation(
        route = Graph.BottomNavigation.Search.route,
        startDestination = Screen.Search.route
    ) {
        addSearch(navController)
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.addMain(navController: NavController) {
    composable(Screen.Main.route) {
        MainScreen(hiltViewModel()) { book ->
            navController.navigateToDetails(book)
        }
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addSearch(navController: NavController) {
    composable(Screen.Search.route) {
        SearchScreen(hiltViewModel()) { book ->
            navController.navigateToDetails(book)
        }
    }
}

private fun NavController.navigateToDetails(book: Book) {
    navigate(Graph.Details.route)
    currentBackStackEntry?.arguments =
        bundleOf(BookDetailsViewModel.ARG_BOOK to book)
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addDetails(navController: NavController) {
    navigation(route = Graph.Details.route, startDestination = Screen.Details.route) {
        composable(
            Screen.Details.route,
            arguments = listOf(navArgument(BookDetailsViewModel.ARG_BOOK) {
                type = NavType.ParcelableType(Book::class.java)
            })
        ) {
            DetailsScreen(viewModel = hiltViewModel(),
                navigateBack = {
                    navController.popBackStack()
                },
                onBuyClick = {
                    //todo
                }
            )
        }
        //todo add bottom sheet
    }
}