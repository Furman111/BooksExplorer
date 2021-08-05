package ru.furman.booksexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.ui.Screens
import ru.furman.booksexplorer.ui.details.DetailsScreen
import ru.furman.booksexplorer.ui.main.MainScreen
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksExplorerApp()
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun BooksExplorerApp() {
        BooksExplorerTheme {
            val navController = rememberNavController()
            BooksExplorerNavHost(navController)
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun BooksExplorerNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.MAIN.name,
            modifier = modifier
        ) {
            composable(Screens.MAIN.name) {
                MainScreen(navController, hiltViewModel())
            }
            composable(
                Screens.DETAILS.name,
                arguments = listOf(navArgument(BookDetailsViewModel.ARG_BOOK) {
                    type = NavType.ParcelableType(Book::class.java)
                })
            ) {
                DetailsScreen(viewModel = hiltViewModel(), navController = navController)
            }
        }
    }

}