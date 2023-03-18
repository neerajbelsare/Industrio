package com.example.industrio.navigation

import com.example.industrio.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object Forum : NavigationItem("forum", R.drawable.ic_forum, "Forum")
    object Track : NavigationItem("track", R.drawable.ic_track, "Track")
    object Profile : NavigationItem("profile", R.drawable.ic_profile, "Profile")
}