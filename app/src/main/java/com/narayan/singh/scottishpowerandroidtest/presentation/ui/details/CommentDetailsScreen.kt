@file:OptIn(ExperimentalMaterial3Api::class)

package com.narayan.singh.scottishpowerandroidtest.presentation.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.narayan.singh.scottishpowerandroidtest.R
import com.narayan.singh.scottishpowerandroidtest.common.theme.Dimens
import com.narayan.singh.scottishpowerandroidtest.common.theme.ScottishPowerAndroidTestTheme
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.presentation.ui.comments.CommentItem
import com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel.CommentDetailsUIState

@Composable
fun CommentDetailsScreen(
    uiState: CommentDetailsUIState,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.comment_details_title)) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.shadow(Dimens.ToolbarElevation)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.comment == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.no_comments_found))
                    }
                }

                else -> {
                    CommentItem(comment = uiState.comment, showDetails = true)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentDetailsScreen() {
    val sampleUIState = CommentDetailsUIState(
        comment = Comment(
            1,
            1,
            "User1",
            "user1@example.com",
            "Test comment 1"
        ),
    )
    ScottishPowerAndroidTestTheme {
        CommentDetailsScreen(sampleUIState)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentDetailsScreen_NoComment() {
    val sampleUIState = CommentDetailsUIState()
    ScottishPowerAndroidTestTheme {
        CommentDetailsScreen(sampleUIState) // when no comment is available
    }
}