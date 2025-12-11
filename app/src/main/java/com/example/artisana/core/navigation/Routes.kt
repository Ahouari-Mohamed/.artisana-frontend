package com.example.artisana.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.artisana.auth.ui.login.LoginScreen
import com.example.artisana.auth.ui.register.RegisterScreen
import com.example.artisana.auth.ui.forgotPassword.ForgotPasswordScreen
import com.example.artisana.auth.ui.forgotPassword.PasswordSuccessScreen
import com.example.artisana.home.ui.details.DetailsScreen
import com.example.artisana.auth.ui.register.CreatePasswordScreen
import com.example.artisana.auth.ui.register.AccountSuccessScreen
import com.example.artisana.auth.viewmodels.AuthViewModel
import com.example.artisana.auth.viewmodels.ForgotPasswordViewModel
import com.example.artisana.features.ui.profile.ProfileScreen
import com.example.artisana.features.ui.cart.CartScreen
import com.example.artisana.features.ui.favorites.FavoritesScreen
import com.example.artisana.features.ui.search.SearchScreen
import com.example.artisana.home.ui.home.HomeScreen
import com.example.artisana.splash.ui.onboarding.OnboardingScreen
import com.example.artisana.splash.ui.welcome.WelcomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    val authViewModel: AuthViewModel = viewModel()
    val isLoggedIn = authViewModel.isUserLoggedIn.collectAsState()

    val startDestination = when (isLoggedIn.value) {
        true -> Screen.Home.route
        false -> Screen.Welcome.route
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            val authViewModel: AuthViewModel = viewModel()
            val isLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

            WelcomeScreen(
                navController = navController,
                isLoggedIn = isLoggedIn
            )
        }



        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(navController)
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.CreatePassword.route) {
            CreatePasswordScreen(navController)
        }

        composable(Screen.SuccessAccount.route) {
            AccountSuccessScreen(navController)
        }

        composable(Screen.ForgotPassword.route) {
            val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
            ForgotPasswordScreen(
                navController = navController,
                viewModel = forgotPasswordViewModel
            )
        }

        composable(Screen.SuccessPass.route) {
            PasswordSuccessScreen(navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: "1"
            DetailsScreen(navController, productId)
        }

        composable(Screen.Cart.route) {
            CartScreen(navController)
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
    }
}
