@file:OptIn(ExperimentalCoroutinesApi::class)

package com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.narayan.singh.scottishpowerandroidtest.common.MainDispatcherRule
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.usecase.GetCommentByIdUseCase
import com.narayan.singh.scottishpowerandroidtest.presentation.navigation.NavigationConstants
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentDetailsViewModelTest {

    private lateinit var viewModel: CommentDetailsViewModel
    private val getCommentByIdUseCase: GetCommentByIdUseCase = mockk()
    private val savedStateHandle = SavedStateHandle(mapOf(NavigationConstants.COMMENT_ID_KEY to "1"))

    @get:Rule
    val testDispatcherRule = MainDispatcherRule()

    private val mockComment = Comment(1, 1, "User1", "user1@example.com", "Test comment")

    @Before
    fun setup() {
        coEvery { getCommentByIdUseCase(1) } coAnswers {
            mockComment
        }
        viewModel = CommentDetailsViewModel(getCommentByIdUseCase, savedStateHandle, testDispatcherRule.testDispatcher)
    }

    @Test
    fun `isLoading should be true initially and then false when comment found update update UI state`() = runTest {
        val stateChanges = mutableListOf<CommentDetailsUIState>()
        val testScope = TestScope()

        // Given: A mock comment with ID 1
        coEvery { getCommentByIdUseCase(1) } coAnswers {
            delay(100)
            mockComment
        }

        val job = testScope.launch(testDispatcherRule.testDispatcher) {
            viewModel = CommentDetailsViewModel(getCommentByIdUseCase, savedStateHandle, testDispatcherRule.testDispatcher)
            viewModel.uiState.toList(stateChanges)
        }

        advanceUntilIdle()

        job.cancel()

        assert(stateChanges.any { it.isLoading })
        Assert.assertEquals(false, stateChanges.last().isLoading)
        assertEquals(mockComment, stateChanges.last().comment)
    }

    @Test
    fun `fetchCommentById should set comment to null when not found`() = runTest {
        // Given: No comment exists with ID 99
        val savedStateHandle = SavedStateHandle(mapOf(NavigationConstants.COMMENT_ID_KEY to "99"))
        coEvery { getCommentByIdUseCase(99) } returns null

        // When: Creating the ViewModel
        viewModel = CommentDetailsViewModel(getCommentByIdUseCase, savedStateHandle, Dispatchers.Unconfined)

        // Then: The UI state should contain null for comment
        val state = viewModel.uiState.first()
        assertEquals(false, state.isLoading)
        assertEquals(null, state.comment)
    }

    @Test
    fun `fetchCommentById should handle invalid commentId`() = runTest {
        // Given: An invalid commentId
        val savedStateHandle = SavedStateHandle(mapOf(NavigationConstants.COMMENT_ID_KEY to "invalid"))
        coEvery { getCommentByIdUseCase(any()) } returns null

        // When: Creating the ViewModel
        viewModel = CommentDetailsViewModel(getCommentByIdUseCase, savedStateHandle, Dispatchers.Unconfined)

        // Then: The UI state should contain null for comment
        val state = viewModel.uiState.first()
        assertEquals(false, state.isLoading)
        assertEquals(null, state.comment)
    }
}