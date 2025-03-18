package com.narayan.singh.scottishpowerandroidtest.data.repository

import com.narayan.singh.scottishpowerandroidtest.data.local.dao.CommentDao
import com.narayan.singh.scottishpowerandroidtest.data.local.entity.CommentEntity
import com.narayan.singh.scottishpowerandroidtest.data.network.CommentApi
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val api: CommentApi,
    private val commentDao: CommentDao
) : CommentRepository {
    override suspend fun getComments(): List<Comment> {
        return try {
            val apiComments = api.getComments().map { it.toDomainModel() }
            commentDao.clearComments()
            commentDao.insertComments(apiComments.map { CommentEntity.fromDomainModel(it) })
            apiComments
        } catch (e: Exception) {
            commentDao.getAllComments().map { it.toDomainModel() }
        }
    }

    override suspend fun getCommentById(commentId: Int): Comment? {
        return commentDao.getCommentById(commentId)?.toDomainModel()
    }
}