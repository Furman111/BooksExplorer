package ru.furman.booksexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.furman.booksexplorer.ui.Screens
import ru.furman.booksexplorer.ui.main.MainScreen
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksExplorerApp()
        }
    }

    @Composable
    fun BooksExplorerApp() {
        BooksExplorerTheme {
            val navController = rememberNavController()
            BooksExplorerNavHost(navController)
        }
    }

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
                MainScreen()
            }
            composable(Screens.DETAILS.name) {

            }
        }
    }

}