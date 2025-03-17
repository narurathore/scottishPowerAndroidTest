package com.narayan.singh.scottishpowerandroidtest.domain.usecase

import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCommentsUseCaseTest {

    private lateinit var getCommentsUseCase: GetCommentsUseCase
    private val repository: CommentRepository = mockk()

    @Before
    fun setup() {
        getCommentsUseCase = GetCommentsUseCase(repository)
    }

    @Test
    fun `invoke should return sorted list of comments`() = runBlocking {
        // Given: Unsorted comments
        val comments = listOf(
            Comment(1, 1, "Zebra", "zebra@example.com", "Body Z"),
            Comment(2, 1, "Apple", "apple@example.com", "Body A"),
            Comment(3, 1, "Monkey", "monkey@example.com", "Body M")
        )
        coEvery { repository.getComments() } returns comments

        // When: Use case is invoked
        val result = getCommentsUseCase()

        // Then: Comments should be sorted alphabetically by name
        assertEquals("Apple", result[0].name)
        assertEquals("Monkey", result[1].name)
        assertEquals("Zebra", result[2].name)
    }
}