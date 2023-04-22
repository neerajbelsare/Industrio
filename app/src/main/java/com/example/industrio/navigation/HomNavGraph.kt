package  com.example.industrio.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.industrio.BottomBarNav
//import androidx.navigation.compose.navigation
import com.example.industrio.screens.AccountNavScreen.AccountScreen
import com.example.industrio.screens.AccountNavScreen.AddressesScreen
import com.example.industrio.screens.AccountNavScreen.CardsScreen
import com.example.industrio.screens.AccountNavScreen.HelpScreen
import com.example.industrio.screens.AccountNavScreen.OffersScreen
import com.example.industrio.screens.AccountNavScreen.OrdersScreen
import com.example.industrio.screens.AccountNavScreen.SettingsScreen
import com.example.industrio.screens.AccountNavScreen.SignOutScreen
import com.example.industrio.screens.HomeScreen.HomeScreen
import com.example.industrio.screens.HomeScreen.ProfileScreen
import com.example.industrio.screens.SignupScreen.ScreenContent

//import com.example.preecureapp.BottomBarScreen
//import com.example.preecureapp.navigation.DoctorScreen
//import com.example.preecureapp.navigation.FormScreen
//import com.example.preecureapp.navigation.Profile
//import com.example.preecureapp.screens.AccountNavScreens.*
//import com.example.preecureapp.screens.AccountNavScreens.ProfileScreen.AccountScreen
//import com.example.preecureapp.screens.AccountNavScreens.ProfileScreen.DoctorForm.DoctorConfirmScreen
//import com.example.preecureapp.screens.AccountNavScreens.ProfileScreen.DoctorForm.DoctorForm
//import com.example.preecureapp.screens.AccountNavScreens.ProfileScreen.DoctorForm.DoctorForm2
//import com.example.industrio.screens.ScreenContent

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarNav.Home.route
    ) {
        composable(route = BottomBarNav.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarNav.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = BottomBarNav.Explore.route) {
            ScreenContent(
                name = BottomBarNav.Explore.route,
                onClick = {

                }
            )
        }
        composable(route = BottomBarNav.Chat.route) {
            ScreenContent(
                name = BottomBarNav.Chat.route,
                onClick = {

                }
            )
        }
        profileNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PROFILE,
        startDestination = Profile.AccountScreen.route
    ) {
        composable(route = Profile.AccountScreen.route) {
            AccountScreen(navController = navController)
        }
        composable(route = Profile.OrdersScreen.route) {
            OrdersScreen(navController = navController)
        }
        composable(route = Profile.AddressesScreen.route) {
            AddressesScreen(navController = navController)
        }
        composable(route = Profile.CardsScreen.route) {
            CardsScreen(navController = navController)
        }
        composable(route = Profile.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = Profile.HelpScreen.route) {
            HelpScreen(navController = navController)
        }
        composable(route = Profile.OffersScreen.route) {
            OffersScreen(navController = navController)
        }
        composable(route = Profile.SignOutScreen.route) {
            SignOutScreen(navController = navController)
        }
//        formNavGraph(navController = navController)
    }
}
