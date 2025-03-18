package com.narayan.singh.scottishpowerandroidtest.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.narayan.singh.scottishpowerandroidtest.data.local.dao.CommentDao
import com.narayan.singh.scottishpowerandroidtest.data.local.database.AppDatabase
import com.narayan.singh.scottishpowerandroidtest.data.network.CommentApi
import com.narayan.singh.scottishpowerandroidtest.data.repository.CommentRepositoryImpl
import com.narayan.singh.scottishpowerandroidtest.data.utils.ApiConstants
import com.narayan.singh.scottishpowerandroidtest.data.utils.DatabaseConstants
import com.narayan.singh.scottishpowerandroidtest.domain.repository.CommentRepository
import com.narayan.singh.scottishpowerandroidtest.domain.usecase.GetCommentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUnsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DatabaseConstants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCommentDao(db: AppDatabase): CommentDao {
        return db.commentDao()
    }

    @Provides
    @Singleton
    fun provideCommentRepository(
        api: CommentApi,
        commentDao: CommentDao
    ): CommentRepository {
        return CommentRepositoryImpl(api, commentDao)
    }

    @Provides
    @Singleton
    fun provideGetCommentsUseCase(repository: CommentRepository): GetCommentsUseCase {
        return GetCommentsUseCase(repository)
    }
}