package com.example.zametkus.presentation.composable

sealed class Screens(
    val route:String
){
    object SplashScreen:Screens("SplashScreen")
    object HomeScreen:Screens("HomeScreen")
}
