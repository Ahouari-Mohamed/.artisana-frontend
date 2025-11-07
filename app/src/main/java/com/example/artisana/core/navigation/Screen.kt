package com.example.artisana.core.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgotpassword")
    object CreatePassword: Screen("createPassword")

    object Success : Screen("Success")
    object Home : Screen("home")
    object Details : Screen("details/{productId}") {
        fun createRoute(productId: String) = "details/$productId"
    }
    object Cart : Screen("cart")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object Search : Screen("search")
}