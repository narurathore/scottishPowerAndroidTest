package com.narayan.singh.scottishpowerandroidtest.presentation.ui.comments

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.narayan.singh.scottishpowerandroidtest.common.theme.ScottishPowerAndroidTestTheme
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentItemTest {

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
    fun commentItem_displaysNameAndBody() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentItem(comment = sampleComment, showDetails = false)
            }
        }

        composeTestRule.onNodeWithText("John Doe").assertExists()
        composeTestRule.onNodeWithText("This is a sample comment for testing.").assertExists()
        composeTestRule.onNode(hasTestTag("commentIdLabel")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("commentEmailLabel")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("horizontalDivider")).assertDoesNotExist()
    }

    @Test
    fun commentItem_invokesClickAction() {
        var clicked = false
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentItem(
                    comment = sampleComment,
                    showDetails = false,
                    onClick = { clicked = true })
            }
        }

        composeTestRule.onNode(hasText("John Doe")).performClick()
        assert(clicked)
    }

    @Test
    fun commentItem_showsDetailsWhenEnabled() {
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentItem(comment = sampleComment, showDetails = true)
            }
        }

        composeTestRule.onNodeWithText("John Doe").assertExists()
        composeTestRule.onNodeWithText("This is a sample comment for testing.").assertExists()
        composeTestRule.onNode(hasTestTag("commentIdLabel"), useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("1").assertExists()
        composeTestRule.onNode(hasTestTag("commentEmailLabel"), useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("john.doe@example.com").assertExists()
        composeTestRule.onAllNodes(hasTestTag("horizontalDivider"), useUnmergedTree = true).assertCountEquals(2)
    }

    @Test
    fun commentItem_doesNotTriggerClickWhenDisabled() {
        var clicked = false
        composeTestRule.setContent {
            ScottishPowerAndroidTestTheme {
                CommentItem(
                    comment = sampleComment,
                    showDetails = false,
                    onClick = { clicked = true },
                    disableClick = true)
            }
        }

        composeTestRule.onNode(hasText("John Doe")).performClick()
        assert(!clicked)
    }

}