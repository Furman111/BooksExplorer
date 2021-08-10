package ru.furman.booksexplorer.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AppContent() {
    val navController = rememberNavController()
    val currentSelectedItem by navController.currentScreenAsState()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val hideBottomBar =
        noBottomBarNavigableList.any { it.route == currentBackStackEntry?.destination?.route }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = !hideBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                AppBottomNavigation(
                    selectedNavigation = currentSelectedItem,
                    onNavigationSelected = { selected ->
                        navController.navigate(selected.route) {
                            launchSingleTop = true
                            restoreState = true

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = if (hideBottomBar) 0.dp else innerPadding.calculateBottomPadding()
                )
                .fillMaxSize()
        ) {
            AppNavigation(navController = navController)
        }
    }
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Graph.BottomNavigation> {
    val selectedItem =
        remember { mutableStateOf<Graph.BottomNavigation>(Graph.BottomNavigation.Main) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Graph.BottomNavigation.Main.route } -> {
                    selectedItem.value = Graph.BottomNavigation.Main
                }
                destination.hierarchy.any { it.route == Graph.BottomNavigation.Search.route } -> {
                    selectedItem.value = Graph.BottomNavigation.Search
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

@Composable
internal fun AppBottomNavigation(
    selectedNavigation: Graph.BottomNavigation,
    onNavigationSelected: (Graph.BottomNavigation) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(modifier = modifier) {
        BottomNavigationItems(
            currentScreen = selectedNavigation,
            onClick = onNavigationSelected
        )
    }
}

@Composable
private fun RowScope.BottomNavigationItems(
    currentScreen: Graph.BottomNavigation,
    onClick: (Graph.BottomNavigation) -> Unit
) {
    bottomNavigationGraphs.forEach { screen ->
        BottomNavigationItem(
            modifier = Modifier.background(MaterialTheme.colors.surface),
            icon = { Icon(screen.icon, contentDescription = null) },
            label = {
                Text(
                    text = stringResource(screen.labelRes),
                    fontWeight = FontWeight.Bold
                )
            },
            selected = currentScreen == screen,
            onClick = { onClick(screen) },
            selectedContentColor = MaterialTheme.colors.onSurface
        )
    }
}