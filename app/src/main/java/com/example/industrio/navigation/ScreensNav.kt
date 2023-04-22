package com.example.industrio.navigation

import java.text.Normalizer

sealed class AuthScreen(val route: String) {
    object SignInScreen: AuthScreen("signin_screen")
    object SignUpScreen: AuthScreen("signup_screen")
    object OnBoard: AuthScreen("onboard_screen")
}

sealed class Profile(val route: String) {
    object AccountScreen: Profile("account_screen")
    object OrdersScreen: Profile("orders_screen")
    object AddressesScreen: Profile("addresses_screen")
    object CardsScreen: Profile("cards_screen")
    object SettingsScreen: Profile("settings_screen")
    object HelpScreen: Profile("help_screen")
    object OffersScreen: Profile("offers_screen")
    object SignOutScreen: Profile("signout_screen")
}

sealed class FormScreen(val route: String) {
    object Technician : FormScreen(route = "Technician")
    object Seller : FormScreen(route = "Seller")
//    object Pharmacy : FormScreen(route = "PHARMACY")
}

sealed class TechnicianScreen(val route: String) {
    object TechnicianForm2 : TechnicianScreen(route = "DOCTOR2")
    object TechnicianConfirm : FormScreen(route = "CONFIRM")
}