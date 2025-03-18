package com.narayan.singh.scottishpowerandroidtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Comments.route) {
        composable(Screen.Comments.route) {
            // Add your comments screen composable here
        }
        composable(Screen.CommentDetails.route) {
            // Add your comment details screen composable here
        }
    }
}
