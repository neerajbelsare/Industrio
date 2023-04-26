package com.example.industrio

import androidx.compose.material.icons.Icons

import androidx.compose.material.*
//import androidx.compose.material.icons.Icons

sealed class BottomBarNav (
    val route: String,
    val icon: Int
) {
    val appIcons = Icons.Outlined

    object Home : BottomBarNav(
        route = "Home",
        icon = R.drawable.ic_home
    )

    object Profile : BottomBarNav(
        route = "Profile",
        icon = R.drawable.ic_profile
    )

    object Explore : BottomBarNav(
        route = "Explore",
        icon =R.drawable.ic_track
    )

    object Chat : BottomBarNav(
        route = "Chat",
        icon =R.drawable.ic_forum
    )
}
