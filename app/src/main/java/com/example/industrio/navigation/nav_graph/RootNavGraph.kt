package com.example.industrio.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.industrio.screens.ForumScreen.ForumViewModel
import com.example.industrio.screens.HomeScreen.HomeScreen
import com.example.industrio.screens.HomeScreen.ProfileViewModel
import com.example.industrio.screens.TechniciansScreen.TechnicianListModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun isUserSignedIn(): Boolean {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser != null
}

@Composable
fun SetupNavGraph(
    navController: NavHostController, viewModel: ForumViewModel, viewModel1: TechnicianListModel
) {
    val isSignedIn = isUserSignedIn()
    val startDest: String = if (isSignedIn) {
        Graph.HOME
    } else {
        Graph.AUTHENTICATION
    }
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDest
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen(viewModel, viewModel1)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val PROFILE = "profile_graph"
    const val FORMS = "form_graph"
    const val TECHNICIAN = "technician_graph"
    const val COMPANY = "company_graph"
    const val FORUM = "forum_graph"
    const val TECHNICIANS = "technicians0_graph"
}