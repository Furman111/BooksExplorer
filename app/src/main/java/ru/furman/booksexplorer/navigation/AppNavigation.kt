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

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @StringRes val labelRes: Int
) {
    object Main : Screen("mainroot", Icons.Filled.Home, R.string.main_label)
    object Search : Screen("searchroot", Icons.Filled.Search, R.string.search_label)
}

val bottomNavigationScreens: List<Screen> =
    listOf(Screen.Main, Screen.Search)

sealed class LeafScreen(val route: String) {
    object Main : LeafScreen("main")
    object Search : LeafScreen("search")
    object Details : LeafScreen("details")
}

val noBottomBarLeafScreens = listOf(LeafScreen.Details)

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
        startDestination = Screen.Main.route,
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
        route = Screen.Main.route,
        startDestination = LeafScreen.Main.route
    ) {
        addMain(navController)
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
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.addMain(navController: NavController) {
    composable(LeafScreen.Main.route) {
        MainScreen(hiltViewModel()) { book ->
            navController.navigateToDetails(book)
        }
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addSearch(navController: NavController) {
    composable(LeafScreen.Search.route) {
        SearchScreen(hiltViewModel()) { book ->
            navController.navigateToDetails(book)
        }
    }
}

private fun NavController.navigateToDetails(book: Book) {
    navigate(LeafScreen.Details.route)
    currentBackStackEntry?.arguments =
        bundleOf(BookDetailsViewModel.ARG_BOOK to book)
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
        DetailsScreen(viewModel = hiltViewModel(),
            navigateBack = {
                navController.popBackStack()
            },
            onBuyClick = {
                //todo
            }
        )
    }
}