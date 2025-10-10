package com.example.artisana.core.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Details : Screen("details/{itemId}") {
        fun createRoute(itemId: String) = "details/$itemId"
    }
}