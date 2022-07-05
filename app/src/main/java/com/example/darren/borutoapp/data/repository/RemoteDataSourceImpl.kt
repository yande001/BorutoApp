package com.example.darren.borutoapp.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.darren.borutoapp.data.local.BorutoDatabase
import com.example.darren.borutoapp.data.local.GoatDatabase
import com.example.darren.borutoapp.data.paging_source.*
import com.example.darren.borutoapp.data.remote.BorutoApi
import com.example.darren.borutoapp.data.remote.GoatApi
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero
import com.example.darren.borutoapp.domain.repository.RemoteDataSource
import com.example.darren.borutoapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase,
    private val goatApi: GoatApi,
    private val goatDatabase: GoatDatabase
): RemoteDataSource
{
    private val heroDao = borutoDatabase.heroDao()
    private val goatDao = goatDatabase.goatDao()


    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchHeroesSource(borutoApi = borutoApi, query = query)
            }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllGoats(): Flow<PagingData<Goat>> {
        val pagingSourceFactory = { goatDao.getAllGoats() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = GoatRemoteMediator2(
                goatApi = goatApi,
                goatDatabase = goatDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchGoats(query: String): Flow<PagingData<Goat>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchGoatsSource(goatApi = goatApi, query = query)
            }
        ).flow
    }

}