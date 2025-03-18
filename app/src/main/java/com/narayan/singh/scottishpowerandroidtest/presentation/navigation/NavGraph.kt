package com.narayan.singh.scottishpowerandroidtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.narayan.singh.scottishpowerandroidtest.presentation.ui.comments.CommentsScreen
import com.narayan.singh.scottishpowerandroidtest.presentation.ui.details.CommentDetailsScreen
import com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel.CommentDetailsViewModel
import com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel.CommentsViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Comments.route) {
        composable(Screen.Comments.route) {
            val viewModel: CommentsViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsState().value
            CommentsScreen(uiState, onCommentSelected = { comment ->
                navController.navigate(Screen.CommentDetails.createRoute(comment.id))
            })
        }
        composable(Screen.CommentDetails.route) {
            val detailsViewModel: CommentDetailsViewModel = hiltViewModel()
            val uiState = detailsViewModel.uiState.collectAsState().value
            CommentDetailsScreen(
                uiState,
                onBackClick = { navController.popBackStack() })
        }
    }
}
