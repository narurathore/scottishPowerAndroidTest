package com.narayan.singh.scottishpowerandroidtest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.narayan.singh.scottishpowerandroidtest.data.local.dao.CommentDao
import com.narayan.singh.scottishpowerandroidtest.data.local.entity.CommentEntity

@Database(entities = [CommentEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao
}