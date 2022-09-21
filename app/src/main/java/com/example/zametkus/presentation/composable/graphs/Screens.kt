package com.example.zametkus.presentation.composable.graphs

sealed class Screens(
    val route: String
) {
    object SplashScreen : Screens("SplashScreen")
    object HomeScreen : Screens("HomeScreen")
    object HistoryScreen: Screens("HistoryScreen")
}
