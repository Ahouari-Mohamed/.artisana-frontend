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
import com.example.artisana.home.ui.details.DetailsScreen
import com.example.artisana.auth.ui.register.CreatePasswordScreen
import com.example.artisana.auth.ui.register.AccountSuccessScreen
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

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController=navController)
        }

        composable(Screen.CreatePassword.route) {
            CreatePasswordScreen(navController=navController)
        }

        composable(Screen.Success.route) {
            AccountSuccessScreen(navController=navController)
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController=navController)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            DetailsScreen(
                navController = navController,
                itemId = itemId
            )
        }
    }
}