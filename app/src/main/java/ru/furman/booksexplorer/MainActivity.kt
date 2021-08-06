package ru.furman.booksexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.ui.BottomNavigationScreen
import ru.furman.booksexplorer.ui.Screens
import ru.furman.booksexplorer.ui.details.DetailsScreen
import ru.furman.booksexplorer.ui.main.MainScreen
import ru.furman.booksexplorer.ui.theme.BooksExplorerTheme
import ru.furman.booksexplorer.viewmodel.details.BookDetailsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val bottomNavigationScreens: List<BottomNavigationScreen> =
        listOf(BottomNavigationScreen.Main, BottomNavigationScreen.Search)

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
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            bottomBar = {
                if (currentDestination?.route != Screens.DETAILS.name) {
                    BottomNavigation {
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
                                selected = currentDestination?.hierarchy?.any {
                                    it.route == screen.screen.name
                                } == true,
                                onClick = {
                                    navController.navigate(screen.screen.name) {
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
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.MAIN.name,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(Screens.MAIN.name) {
                    MainScreen(navController, hiltViewModel())
                }
                composable(Screens.SEARCH.name) {

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

}