package com.example.banka_3_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.banka_3_mobile.home.homePage
import com.example.banka_3_mobile.login.loginPage

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login",
        /*enterTransition = {
            slideInHorizontally(
                animationSpec = spring(),
                initialOffsetX = { it },
            )
        },
        exitTransition = { scaleOut(targetScale = 0.75f) },
        popEnterTransition = { scaleIn(initialScale = 0.75f) },
        popExitTransition = { slideOutHorizontally { it } },*/
    ) {

        loginPage(route = "login",
            navController = navController)
        homePage(route = "homepage", navController = navController)
    }
}