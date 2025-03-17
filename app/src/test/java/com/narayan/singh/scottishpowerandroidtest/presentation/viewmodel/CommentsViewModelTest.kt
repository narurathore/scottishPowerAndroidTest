package com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel

import com.narayan.singh.scottishpowerandroidtest.common.MainDispatcherRule
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.usecase.GetCommentsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
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
        coEvery { getCommentsUseCase() } returns commentsList
        viewModel = CommentsViewModel(getCommentsUseCase, testDispatcherRule.testDispatcher)
    }

    @Test
    fun `viewModel should fetch comments on initialization`() = runTest {
        // Then: UI state should have updated with comments
        val state = viewModel.uiState.first()
        assertEquals(false, state.isLoading)
        assertEquals(2, state.comments.size)
    }
}