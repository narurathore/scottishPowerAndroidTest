package com.narayan.singh.scottishpowerandroidtest.presentation.navigation

sealed class Screen(val route: String) {
    data object Comments : Screen(NavigationConstants.COMMENTS_SCREEN)
    data object CommentDetails : Screen(NavigationConstants.COMMENT_DETAILS_SCREEN) {
        fun createRoute(commentId: Int) = NavigationConstants.createCommentDetailsRoute(commentId)
    }
}