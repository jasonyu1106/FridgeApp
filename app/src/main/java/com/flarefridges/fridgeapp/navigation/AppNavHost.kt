package com.flarefridges.fridgeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flarefridges.fridgeapp.drawer.DrawerDetail
import com.flarefridges.fridgeapp.homepage.Home

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = "home") {
            Home(
                onNavigateToDrawerItems = { drawerId ->
                    navController.navigate(route = "drawer_items/${drawerId}")
                }
            )
        }
        composable(
            route = "drawer_items/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            DrawerDetail(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}