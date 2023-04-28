package com.example.industrio.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.industrio.screens.SigninScreen.SignInScreen
import com.example.industrio.screens.SignupScreen.SignUpScreen
import com.example.industrio.navigation.AuthScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SignInScreen.route
    ) {
        composable(route = AuthScreen.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        composable(route = AuthScreen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
    }
}