package com.narayan.singh.scottishpowerandroidtest.di

import com.narayan.singh.scottishpowerandroidtest.data.network.CommentApi
import com.narayan.singh.scottishpowerandroidtest.data.repository.CommentRepositoryImpl
import com.narayan.singh.scottishpowerandroidtest.data.utils.ApiConstants
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import com.narayan.singh.scottishpowerandroidtest.domain.usecase.GetCommentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCommentApi(retrofit: Retrofit): CommentApi {
        return retrofit.create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentRepository(api: CommentApi): CommentRepository {
        return CommentRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetCommentsUseCase(repository: CommentRepository): GetCommentsUseCase {
        return GetCommentsUseCase(repository)
    }
}