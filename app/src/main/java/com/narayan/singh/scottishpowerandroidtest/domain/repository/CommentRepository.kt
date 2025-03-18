package com.narayan.singh.scottishpowerandroidtest.domain.repository

import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment

interface CommentRepository {
    suspend fun getComments(): List<Comment>
    suspend fun getCommentById(commentId: Int): Comment?
}