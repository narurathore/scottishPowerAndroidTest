package com.narayan.singh.scottishpowerandroidtest.domain.usecase

import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentByIdUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(commentId: Int): Comment? {
        return repository.getCommentById(commentId)
    }
}