package com.example.zametkus.presentation.composable

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zametkus.presentation.ZamViewModel
import com.example.zametkus.presentation.composable.homeScreen.HomeScreen
import com.example.zametkus.presentation.composable.splashScreen.AnimatedSplashScreen

@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController:NavHostController,
    viewModel:ZamViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ){
        composable(
            route = Screens.SplashScreen.route
        ){
            AnimatedSplashScreen(navController = navController)
        }
        composable(
            route = Screens.HomeScreen.route
        ){
            HomeScreen(
                viewModel = viewModel
            )
        }
    }
}