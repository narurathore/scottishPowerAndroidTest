package com.narayan.singh.scottishpowerandroidtest.data.repository

import com.narayan.singh.scottishpowerandroidtest.data.network.CommentApi
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val api: CommentApi
) : CommentRepository {
    override suspend fun getComments(): List<Comment> {
        return api.getComments().map { it.toDomainModel() }
    }
}