package com.narayan.singh.scottishpowerandroidtest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.narayan.singh.scottishpowerandroidtest.data.local.entity.CommentEntity
import com.narayan.singh.scottishpowerandroidtest.data.utils.DatabaseConstants

@Dao
interface CommentDao {

    @Query("SELECT * FROM ${DatabaseConstants.COMMENTS_TABLE_NAME}")
    suspend fun getAllComments(): List<CommentEntity>

    @Query("SELECT * FROM ${DatabaseConstants.COMMENTS_TABLE_NAME} WHERE id = :commentId") // ✅ Fetch single comment
    suspend fun getCommentById(commentId: Int): CommentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Query("DELETE FROM ${DatabaseConstants.COMMENTS_TABLE_NAME}")
    suspend fun clearComments()
}