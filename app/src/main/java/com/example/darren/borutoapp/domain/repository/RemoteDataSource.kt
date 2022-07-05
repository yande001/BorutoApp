package com.example.darren.borutoapp.domain.repository

import androidx.paging.PagingData
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllHeroes(): Flow<PagingData<Hero>>
    fun searchHeroes(query: String): Flow<PagingData<Hero>>

    fun getAllGoats(): Flow<PagingData<Goat>>
    fun searchGoats(query: String): Flow<PagingData<Goat>>
}