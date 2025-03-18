package com.narayan.singh.scottishpowerandroidtest.presentation.ui.details

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.narayan.singh.scottishpowerandroidtest.common.theme.ScottishPowerAndroidTestTheme
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel.CommentDetailsUIState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var sampleComment: Comment

    @Before
    fun setup() {
        sampleComment = Comment(
            id = 1,
            postId = 1,
            name = "John Doe",
            email = "john.doe@example.com",
            body = "This is a sample comment for testing."
        )
    }

    @Test
    fun commentDetailsScreen_displaysLoadingIndicatorWhenLoading() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentDetailsScreen(CommentDetailsUIState(isLoading = true))
            }
        }

        composeTestRule.onNode(hasTestTag("loadingIndicator")).assertExists()
    }

    @Test
    fun commentDetailsScreen_displaysNoCommentsMessageWhenNull() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentDetailsScreen(CommentDetailsUIState(comment = null))
            }
        }

        composeTestRule.onNode(hasTestTag("emptyStateMessage")).assertExists()
    }

    @Test
    fun commentDetailsScreen_displaysCommentDetails() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentDetailsScreen(CommentDetailsUIState(comment = sampleComment))
            }
        }

        composeTestRule.onNodeWithText("John Doe").assertExists()
        composeTestRule.onNodeWithText("This is a sample comment for testing.").assertExists()
        composeTestRule.onNodeWithText("ID:").assertExists()
        composeTestRule.onNodeWithText("1").assertExists()
        composeTestRule.onNodeWithText("Email:").assertExists()
        composeTestRule.onNodeWithText("john.doe@example.com").assertExists()
    }

    @Test
    fun commentDetailsScreen_triggersOnBackClick() {
        var backClicked = false
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentDetailsScreen(CommentDetailsUIState(comment = sampleComment), onBackClick = { backClicked = true })
            }
        }

        composeTestRule.onNode(hasTestTag("backButton")).performClick()
        assert(backClicked)
    }
}