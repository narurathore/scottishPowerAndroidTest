@file:OptIn(ExperimentalMaterial3Api::class)

package com.narayan.singh.scottishpowerandroidtest.presentation.ui.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.narayan.singh.scottishpowerandroidtest.R
import com.narayan.singh.scottishpowerandroidtest.common.theme.Dimens
import com.narayan.singh.scottishpowerandroidtest.common.theme.ScottishPowerAndroidTestTheme
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel.CommentsUIState

@Composable
fun CommentsScreen(
    uiState: CommentsUIState,
    onCommentSelected: (Comment) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.comments_screen_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.shadow(Dimens.ToolbarElevation)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.testTag("loadingIndicator"))
                uiState.comments.isEmpty() -> Text(
                    stringResource(R.string.no_comments_found),
                    modifier = Modifier.testTag("emptyStateMessage")
                )

                else -> CommentsList(uiState.comments, onCommentSelected)
            }
        }
    }
}

@Composable
fun CommentsList(comments: List<Comment>, onCommentSelected: (Comment) -> Unit = {}) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(comments) { comment ->
            CommentItem(comment) {
                onCommentSelected(comment)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentsScreen() {
    val sampleUIState = CommentsUIState(
        comments = listOf(
            Comment(1, 1, "User1", "user1@example.com", "Test comment 1"),
            Comment(2, 1, "User2", "user2@example.com", "Test comment 2")
        )
    )
    ScottishPowerAndroidTestTheme {
        CommentsScreen(sampleUIState)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentsList() {
    val sampleComments = listOf(
        Comment(1, 1, "User1", "user1@example.com", "Test comment 1"),
        Comment(2, 1, "User2", "user2@example.com", "Test comment 2")
    )
    ScottishPowerAndroidTestTheme {
        CommentsList(sampleComments)
    }
}