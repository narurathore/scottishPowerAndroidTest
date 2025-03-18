package com.narayan.singh.scottishpowerandroidtest.data.repository

import com.narayan.singh.scottishpowerandroidtest.data.local.dao.CommentDao
import com.narayan.singh.scottishpowerandroidtest.data.local.entity.CommentEntity
import com.narayan.singh.scottishpowerandroidtest.data.model.CommentDto
import com.narayan.singh.scottishpowerandroidtest.data.network.CommentApi
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CommentRepositoryImplTest {

    private lateinit var repository: CommentRepositoryImpl
    private val mockApi: CommentApi = mockk()
    private val mockDao: CommentDao = mockk()

    private val sampleComments = listOf(
        Comment(1, 1, "User1", "user1@example.com", "Test comment 1"),
        Comment(2, 1, "User2", "user2@example.com", "Test comment 2")
    )

    private val sampleCommentEntities = sampleComments.map { CommentEntity.fromDomainModel(it) }
    private val sampleCommentDtos = sampleComments.map { CommentDto(it.postId, it.id, it.name, it.email, it.body) }

    @Before
    fun setup() {
        repository = CommentRepositoryImpl(mockApi, mockDao)
    }

    @Test
    fun `getComments should fetch from API and cache in database`() = runTest {
        // Given: API call succeeds and returns a list of comments
        coEvery { mockApi.getComments() } returns sampleCommentDtos
        coEvery { mockDao.clearComments() } returns Unit
        coEvery { mockDao.insertComments(sampleCommentEntities) } returns Unit

        // When: Fetching comments
        val result = repository.getComments()

        // Then: API should be called and data should be inserted into the database
        coVerify(exactly = 1) { mockApi.getComments() }
        coVerify(exactly = 1) { mockDao.clearComments() }
        coVerify(exactly = 1) { mockDao.insertComments(sampleCommentEntities) }
        assertEquals(sampleComments, result)
    }

    @Test
    fun `getComments should fetch from database when API fails`() = runTest {
        // Given: API call fails and database has cached comments
        coEvery { mockApi.getComments() } throws Exception("Network Error")
        coEvery { mockDao.getAllComments() } returns sampleCommentEntities

        // When: Fetching comments
        val result = repository.getComments()

        // Then: Verify API was called but failed
        coVerify(exactly = 1) { mockApi.getComments() }
        coVerify(exactly = 1) { mockDao.getAllComments() }
        assertEquals(sampleComments, result)
    }

    @Test
    fun `getCommentById should return a comment from database`() = runTest {
        // Given: A comment exists in the database
        coEvery { mockDao.getCommentById(1) } returns sampleCommentEntities[0]

        // When: Fetching comment by ID
        val result = repository.getCommentById(1)

        // Then: Verify DAO call
        coVerify(exactly = 1) { mockDao.getCommentById(1) }
        assertEquals(sampleComments[0], result)
    }

    @Test
    fun `getCommentById should return null when comment does not exist`() = runTest {
        // Given: No comment exists in the database
        coEvery { mockDao.getCommentById(99) } returns null

        // When: Fetching comment by ID
        val result = repository.getCommentById(99)

        // Then: Verify DAO call
        coVerify(exactly = 1) { mockDao.getCommentById(99) }
        assertEquals(null, result)
    }
}