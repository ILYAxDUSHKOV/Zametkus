package com.example.zametkus.presentation.composable

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.zametkus.presentation.ZamViewModel
import com.example.zametkus.presentation.composable.graphs.Screens
import com.example.zametkus.presentation.composable.historyScreen.HistoryScreen
import com.example.zametkus.presentation.composable.homeScreen.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navControllerForAnimatedGraph: NavHostController,
    viewModel: ZamViewModel
) {
    AnimatedNavHost(
        navController = navControllerForAnimatedGraph,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(
            enterTransition = { initial, _ ->
                slideInHorizontally(
                    initialOffsetX = {-300},
                    animationSpec = tween(300)
                )
            },
            route = Screens.HomeScreen.route,

        ) {
            HomeScreen(
                viewModel = viewModel,
                navController = navControllerForAnimatedGraph
            )
        }
        composable(
            enterTransition = { initial, _ ->
                slideInHorizontally(
                    initialOffsetX = {300},
                    animationSpec = tween(300)
                )
            },
            route = Screens.HistoryScreen.route
        ){
            HistoryScreen(viewModel)
        }
    }
}