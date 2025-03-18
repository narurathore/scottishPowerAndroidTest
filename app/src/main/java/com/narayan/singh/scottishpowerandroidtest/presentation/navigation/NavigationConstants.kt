package com.narayan.singh.scottishpowerandroidtest.presentation.navigation

object NavigationConstants {
    const val COMMENTS_SCREEN = "comments"
    const val COMMENT_ID_KEY = "commentId"
    const val COMMENT_DETAILS_SCREEN = "details/{$COMMENT_ID_KEY}"


    fun createCommentDetailsRoute(commentId: Int) = "details/$commentId"
}