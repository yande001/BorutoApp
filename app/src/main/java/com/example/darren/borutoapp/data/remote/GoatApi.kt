package com.example.darren.borutoapp.data.remote

import com.example.darren.borutoapp.domain.model.ApiResponse
import com.example.darren.borutoapp.domain.model.ApiResponseGoat
import retrofit2.http.GET
import retrofit2.http.Query

interface GoatApi {

    @GET("/goats")
    suspend fun getAllGoats(
        @Query("page") page: Int = 1
    ): ApiResponseGoat

    @GET("/goats/search")
    suspend fun searchGoats(
        @Query("name") name: String
    ): ApiResponseGoat
}