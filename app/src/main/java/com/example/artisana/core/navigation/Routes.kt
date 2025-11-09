package com.example.artisana.core.navigation

import androidx.compose.runtime.Composable
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
    startDestination: String = Screen.Welcome.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController=navController)
        }

        composable(Screen.CreatePassword.route) {
            CreatePasswordScreen(navController=navController)
        }

        composable(Screen.SuccessAccount.route) {
            AccountSuccessScreen(navController=navController)
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController=navController)
        }

        composable(Screen.SuccessPass.route) {
            PasswordSuccessScreen(navController=navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            DetailsScreen(
                navController = navController,
                productId = productId ?: "1"
            )
        }

        composable(route = Screen.Cart.route) {
            CartScreen(navController = navController)
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(navController = navController)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController)
        }
    }
}