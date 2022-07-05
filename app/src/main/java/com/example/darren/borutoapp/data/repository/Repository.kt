package com.example.darren.borutoapp.data.repository

import android.util.Log
import androidx.paging.PagingData
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero
import com.example.darren.borutoapp.domain.repository.DataStoreOperations
import com.example.darren.borutoapp.domain.repository.LocalDataSource
import com.example.darren.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {

    fun getAllGoats(): Flow<PagingData<Goat>>{
        return remote.getAllGoats()
    }

    fun searchGoats(query: String): Flow<PagingData<Goat>>{
        return remote.searchGoats(query = query)
    }

    fun getAllHeroes(): Flow<PagingData<Hero>>{
        return remote.getAllHeroes()
    }

    fun searchHeroes(query: String): Flow<PagingData<Hero>>{
        return remote.searchHeroes(query = query)
    }

    suspend fun getSelectedHero(heroId: Int): Hero {
        return local.getSelectedHero(heroId = heroId)
    }

    suspend fun getSelectedGoat(goatId: Int): Goat{
        return local.getSelectedGoat(goatId = goatId)
    }

    suspend fun saveOnBoardingState(completed: Boolean){
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean>{
        return dataStore.readOnBoardingState()
    }

}