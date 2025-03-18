package com.narayan.singh.scottishpowerandroidtest.presentation.ui.comments

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.narayan.singh.scottishpowerandroidtest.common.theme.ScottishPowerAndroidTestTheme
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel.CommentsUIState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var sampleComments: List<Comment>

    @Before
    fun setup() {
        sampleComments = listOf(
            Comment(1, 1, "User1", "user1@example.com", "Test comment 1"),
            Comment(2, 1, "User2", "user2@example.com", "Test comment 2")
        )
    }

    @Test
    fun commentsScreen_displaysLoadingIndicatorWhenLoading() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentsScreen(CommentsUIState(isLoading = true))
            }
        }

        composeTestRule.onNode(isRoot()).printToLog("ComposeTest") // Debugging log
        composeTestRule.onNode(hasTestTag("loadingIndicator")).assertExists()
    }

    @Test
    fun commentsScreen_displaysNoCommentsMessageWhenListIsEmpty() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentsScreen(CommentsUIState(comments = emptyList()))
            }
        }

        composeTestRule.onNodeWithText("No comments found").assertExists()
    }

    @Test
    fun commentsScreen_displaysCommentsList() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentsScreen(CommentsUIState(comments = sampleComments))
            }
        }

        composeTestRule.onNodeWithText("User1").assertExists()
        composeTestRule.onNodeWithText("Test comment 1").assertExists()
        composeTestRule.onNodeWithText("User2").assertExists()
        composeTestRule.onNodeWithText("Test comment 2").assertExists()
    }

    @Test
    fun commentsScreen_invokesOnCommentSelectedWhenClicked() {
        var selectedComment: Comment? = null
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentsScreen(
                    CommentsUIState(comments = sampleComments),
                    onCommentSelected = { selectedComment = it }
                )
            }
        }

        composeTestRule.onNodeWithText("User1").performClick()
        assert(selectedComment?.id == 1)
    }
}