package com.example.industrio.navigation

sealed class Screens(val route: String) {
    object Signin: Screens("signin_screen")
    object Signup: Screens("signup_screen")
    object Home: Screens("home_screen")
}