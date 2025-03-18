@file:OptIn(ExperimentalMaterial3Api::class)

package com.narayan.singh.scottishpowerandroidtest.presentation.ui.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                uiState.isLoading -> CircularProgressIndicator()
                uiState.comments.isEmpty() -> Text(stringResource(R.string.no_comments_found))
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

@Composable
fun CommentItem(comment: Comment, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.SmallPadding)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = comment.name, style = MaterialTheme.typography.titleLarge)
            Text(text = comment.body, style = MaterialTheme.typography.bodyLarge)
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

@Preview(showBackground = true)
@Composable
fun PreviewCommentItem() {
    ScottishPowerAndroidTestTheme {
        CommentItem(
            comment = Comment(1, 1, "User1", "user1@example.com", "Test comment 1"),
            onClick = {}
        )
    }
}