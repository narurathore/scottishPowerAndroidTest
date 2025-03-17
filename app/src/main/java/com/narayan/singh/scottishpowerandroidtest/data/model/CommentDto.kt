package com.narayan.singh.scottishpowerandroidtest.data.model

import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment

data class CommentDto(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) {
    fun toDomainModel(): Comment {
        return Comment(
            id = id,
            postId = postId,
            name = name,
            email = email,
            body = body
        )
    }
}