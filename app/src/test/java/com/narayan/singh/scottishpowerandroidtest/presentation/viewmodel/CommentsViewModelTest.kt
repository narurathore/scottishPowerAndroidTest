@file:OptIn(ExperimentalCoroutinesApi::class)

package com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel

import com.narayan.singh.scottishpowerandroidtest.common.MainDispatcherRule
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.usecase.GetCommentsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentsViewModelTest {

    private lateinit var viewModel: CommentsViewModel
    private val getCommentsUseCase: GetCommentsUseCase = mockk()

    @get:Rule
    val testDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        // Given: Mock successful API call
        val commentsList = listOf(
            Comment(1, 1, "User1", "user1@example.com", "Test comment"),
            Comment(2, 1, "User2", "user2@example.com", "Another test")
        )
        coEvery { getCommentsUseCase() } coAnswers {
            delay(100) // so all states of ui state can be captured
            commentsList
        }
        viewModel = CommentsViewModel(getCommentsUseCase, testDispatcherRule.testDispatcher)
    }

    @Test
    fun `isLoading should be true initially and then false when comments are fetched`() = runTest {
        val stateChanges = mutableListOf<CommentsUIState>()
        val testScope = TestScope()
        val job = testScope.launch(testDispatcherRule.testDispatcher) {
            viewModel = CommentsViewModel(getCommentsUseCase, testDispatcherRule.testDispatcher)
            viewModel.uiState.toList(stateChanges)
        }

        advanceUntilIdle()

        job.cancel()

        assert(stateChanges.any { it.isLoading })
        assertEquals(false, stateChanges.last().isLoading)
    }
}