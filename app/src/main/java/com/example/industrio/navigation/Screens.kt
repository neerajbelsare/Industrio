package com.example.industrio.navigation

sealed class AuthScreen(val route: String) {
    object SignInScreen: AuthScreen("signin_screen")
    object SignUpScreen: AuthScreen("signup_screen")
}

sealed class Profile(val route: String) {
    object AccountScreen: Profile("account_screen")
    object DashboardScreen: Profile("health_screen")
    object OrdersScreen: Profile("orders_screen")
    object AddressesScreen: Profile("addresses_screen")
    object CardsScreen: Profile("cards_screen")
    object SettingsScreen: Profile("settings_screen")
    object HelpScreen: Profile("help_screen")
    object OffersScreen: Profile("offers_screen")
}

sealed class FormScreen(val route: String) {
    object Technician : FormScreen(route = "TECHNICIAN")
    object Company : FormScreen(route = "COMPANY")
}

sealed class TechnicianScreen(val route: String) {
    object TechnicianForm2 : TechnicianScreen(route = "TECHNICIAN2")
    object TechnicianConfirm : TechnicianScreen(route = "CONFIRM")
}

sealed class CompanyScreen(val route: String) {
    object CompanyForm2 : CompanyScreen(route = "COMPANY2")
    object CompanyConfirm : CompanyScreen(route = "CONFIRM")
}