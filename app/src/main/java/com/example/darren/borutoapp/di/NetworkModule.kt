package com.example.darren.borutoapp.di

import com.example.darren.borutoapp.data.local.BorutoDatabase
import com.example.darren.borutoapp.data.local.GoatDatabase
import com.example.darren.borutoapp.data.remote.BorutoApi
import com.example.darren.borutoapp.data.remote.GoatApi
import com.example.darren.borutoapp.data.repository.RemoteDataSourceImpl
import com.example.darren.borutoapp.domain.repository.RemoteDataSource
import com.example.darren.borutoapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit{
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit): BorutoApi{
        return retrofit.create(BorutoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGoatApi(retrofit: Retrofit): GoatApi {
        return retrofit.create(GoatApi::class.java)
    }




    @Provides
    @Singleton
    fun provideRemoteDataSource(
        borutoApi: BorutoApi,
        borutoDatabase: BorutoDatabase,
        goatApi: GoatApi,
        goatDatabase: GoatDatabase
    ): RemoteDataSource{
        return RemoteDataSourceImpl(
            borutoApi = borutoApi,
            borutoDatabase = borutoDatabase,
            goatApi = goatApi,
            goatDatabase = goatDatabase
        )
    }


}