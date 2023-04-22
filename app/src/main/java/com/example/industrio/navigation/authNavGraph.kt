package com.example.industrio.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.industrio.screens.SigninScreen.SignInScreen
import com.example.preecure.screens.SignupScreen.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.OnBoard.route
    ) {
        composable(route = AuthScreen.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        composable(route = AuthScreen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route= AuthScreen.OnBoard.route){

        }
    }
}