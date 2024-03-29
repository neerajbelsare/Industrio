package com.example.industrio.navigation.nav_graph

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.industrio.navigation.CompanyScreen
import com.example.industrio.navigation.FormScreen
import com.example.industrio.navigation.Profile
import com.example.industrio.navigation.TechnicianScreen
import com.example.industrio.screens.AccountNavScreens.AddressesScreen
import com.example.industrio.screens.AccountNavScreens.CardsScreen
import com.example.industrio.screens.AccountNavScreens.DashboardScreen
import com.example.industrio.screens.AccountNavScreens.HelpScreen
import com.example.industrio.screens.AccountNavScreens.OffersScreen
import com.example.industrio.screens.AccountNavScreens.OrdersScreen
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.AccountScreen
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.CompanyForm.CompanyConfirmScreen
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.CompanyForm.CompanyForm
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.CompanyForm.CompanyForm2
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm.TechnicianConfirmScreen
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm.TechnicianForm
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm.TechnicianForm2
import com.example.industrio.screens.AccountNavScreens.SettingsScreen
import com.example.industrio.screens.ForumScreen.ForumViewModel
import com.example.industrio.screens.HomeScreen.ChatScreen
import com.example.industrio.screens.HomeScreen.ExploreScreen
import com.example.industrio.screens.HomeScreen.MainScreen
import com.example.industrio.screens.HomeScreen.QuestionDetailsScreen
import com.example.industrio.screens.HomeScreen.QuestionFormScreen
import com.example.industrio.screens.HomeScreen.TechnicianDetailsScreen
import com.example.industrio.screens.HomeScreen.TechnicianListScreen
import com.example.industrio.screens.TechniciansScreen.TechnicianListModel

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PROFILE,
        startDestination = Profile.AccountScreen.route
    ) {
        composable(route = Profile.AccountScreen.route) {
            AccountScreen(navController = navController)
        }
        composable(route = Profile.DashboardScreen.route) {
            DashboardScreen(navController = navController)
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
        formNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.formNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.FORMS,
        startDestination = FormScreen.Technician.route
    ) {
        composable(route = FormScreen.Technician.route) {
            TechnicianForm(navController = navController)
        }
        composable(route = FormScreen.Company.route) {
            CompanyForm(navController = navController)
        }
    }
    technicianNavGraph(navController = navController)
    companyNavGraph(navController = navController)
}
fun NavGraphBuilder.technicianNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.TECHNICIAN,
        startDestination = TechnicianScreen.TechnicianForm2.route
    ) {
        composable(route = TechnicianScreen.TechnicianForm2.route) {
            TechnicianForm2(navController = navController)
        }
        composable(route = TechnicianScreen.TechnicianConfirm.route) {
            TechnicianConfirmScreen()
        }
    }
}

fun NavGraphBuilder.companyNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.COMPANY,
        startDestination = CompanyScreen.CompanyForm2.route
    ) {
        composable(route = CompanyScreen.CompanyForm2.route) {
            CompanyForm2(navController = navController)
        }
        composable(route = CompanyScreen.CompanyConfirm.route) {
            CompanyConfirmScreen()
        }
    }
}

fun NavGraphBuilder.forumNavGraph(navController: NavHostController, viewModel: ForumViewModel) {

    navigation(
        route = Graph.FORUM,
        startDestination = "main"
    ) {
        composable("main") {
            ChatScreen(viewModel, navController)
        }
        composable("questionForm") {
            QuestionFormScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            "questionDetails/{questionId}",
            arguments = listOf(navArgument("questionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
            val question = viewModel.questions.value?.find { it.id == questionId }
            if (question != null) {
                QuestionDetailsScreen(viewModel, question, navController)
            } else {
                // Handle question not found case
            }
        }
    }
}

fun NavGraphBuilder.technicianNavGraph(navController: NavHostController, viewModel: TechnicianListModel) {

    navigation(
        route = Graph.TECHNICIANS,
        startDestination = "major"
    ) {
        composable("major") {
            ExploreScreen(viewModel, navController)
        }
        composable("technicianList") {
            TechnicianListScreen(viewModel, navController)
        }
        composable(
            "technicianDetails/{technicianName}",
            arguments = listOf(navArgument("technicianName") { type = NavType.StringType })
        ) { backStackEntry ->
            val techncianName = backStackEntry.arguments?.getString("technicianName")
            val technician = viewModel.technicians.value?.find { it.name == techncianName }
            if (technician != null) {
                TechnicianDetailsScreen(viewModel, technician, navController)
            } else {
                // Handle question not found case
            }
        }
    }
}
