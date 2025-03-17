package com.narayan.singh.scottishpowerandroidtest.domain.usecase

import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(): List<Comment> {
        return repository.getComments().sortedBy { it.name }
    }
}