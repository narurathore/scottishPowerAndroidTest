package com.narayan.singh.scottishpowerandroidtest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.narayan.singh.scottishpowerandroidtest.data.utils.DatabaseConstants
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment

@Entity(tableName = DatabaseConstants.COMMENTS_TABLE_NAME)
data class CommentEntity(
    @PrimaryKey val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
) {
    fun toDomainModel(): Comment {
        return Comment(id, postId, name, email, body)
    }

    companion object {
        fun fromDomainModel(comment: Comment): CommentEntity {
            return CommentEntity(comment.id, comment.postId, comment.name, comment.email, comment.body)
        }
    }
}