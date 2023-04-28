package com.example.industrio.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.industrio.BottomBarScreen
import com.example.industrio.navigation.nav_graph.Graph
import com.example.industrio.navigation.nav_graph.profileNavGraph

@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {innerPadding ->
//        HomeNavGraph(navController = navController)
        NavHost(
            navController = navController,
            route = Graph.HOME,
            startDestination = BottomBarScreen.Home.route
        ) {
            composable(route = BottomBarScreen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(route = BottomBarScreen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable(route = BottomBarScreen.Explore.route) {
                ExploreScreen(navController = navController)
            }
            composable(route = BottomBarScreen.Chat.route) {
                ChatScreen(navController = navController)
            }

            profileNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Chat,
        BottomBarScreen.Explore,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            modifier = Modifier
                .background(Color.White),
            contentColor = Color.Black
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 7.dp, bottom = 7.dp, start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(40.dp)),
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}