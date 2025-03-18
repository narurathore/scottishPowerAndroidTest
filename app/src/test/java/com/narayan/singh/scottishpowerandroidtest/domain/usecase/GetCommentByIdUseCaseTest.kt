package com.narayan.singh.scottishpowerandroidtest.domain.usecase

import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCommentByIdUseCaseTest {

    private lateinit var getCommentByIdUseCase: GetCommentByIdUseCase
    private val repository: CommentRepository = mockk()

    @Before
    fun setup() {
        getCommentByIdUseCase = GetCommentByIdUseCase(repository)
    }

    @Test
    fun `invoke should return comment when found`() = runTest {
        // Given: A mock comment with ID 1
        val mockComment = Comment(1, 1, "User1", "user1@example.com", "Test comment")
        coEvery { repository.getCommentById(1) } returns mockComment

        // When: Calling the use case
        val result = getCommentByIdUseCase(1)

        // Then: The correct comment should be returned
        assertEquals(mockComment, result)
        assertEquals(1, result?.id)
        assertEquals("User1", result?.name)
    }

    @Test
    fun `invoke should return null when comment is not found`() = runTest {
        // Given: No comment exists with ID 99
        coEvery { repository.getCommentById(99) } returns null

        // When: Calling the use case
        val result = getCommentByIdUseCase(99)

        // Then: Result should be null
        assertEquals(null, result)
    }
}
