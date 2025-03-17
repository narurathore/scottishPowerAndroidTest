package com.narayan.singh.scottishpowerandroidtest.data.network

import com.narayan.singh.scottishpowerandroidtest.data.model.CommentDto
import com.narayan.singh.scottishpowerandroidtest.data.utils.ApiConstants.COMMENTS_ENDPOINT
import retrofit2.http.GET

interface CommentApi {
    @GET(COMMENTS_ENDPOINT)
    suspend fun getComments(): List<CommentDto>
}