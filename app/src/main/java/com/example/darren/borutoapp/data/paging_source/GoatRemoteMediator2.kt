package com.example.darren.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.darren.borutoapp.data.local.BorutoDatabase
import com.example.darren.borutoapp.data.local.GoatDatabase
import com.example.darren.borutoapp.data.remote.BorutoApi
import com.example.darren.borutoapp.data.remote.GoatApi
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.GoatRemoteKeys
import com.example.darren.borutoapp.domain.model.Hero
import com.example.darren.borutoapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GoatRemoteMediator2 @Inject constructor(
    private val goatApi: GoatApi,
    private val goatDatabase: GoatDatabase
) : RemoteMediator<Int, Goat>() {

    private val goatDao = goatDatabase.goatDao()
    private val goatRemoteKeysDao = goatDatabase.goatRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = goatRemoteKeysDao.getRemoteKeys(goatId = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 1440

//        Log.d("RemoteMediator", "Current Time: ${parseMillis(currentTime)}")
//        Log.d("RemoteMediator", "Last Updated: ${parseMillis(lastUpdated)}")

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if(diffInMinutes.toInt() <= cacheTimeout){
            InitializeAction.SKIP_INITIAL_REFRESH
        } else{
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Goat>): MediatorResult {
        return try{
            val page = when (loadType){
                LoadType.REFRESH ->{
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND ->{
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND ->{
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }

            }

            val response = goatApi.getAllGoats(page = page)
            if (response.goats.isNotEmpty()){
                goatDatabase.withTransaction {
                    if(loadType == LoadType.REFRESH){
                        goatDao.deleteAllGoats()
                        goatRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prePage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.goats.map {
                            goat ->
                        GoatRemoteKeys(
                            id = goat.id,
                            prevPage = prePage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    goatRemoteKeysDao.addAllRemoteKeys(goatRemoteKeys = keys)
                    goatDao.addGoats(goats = response.goats)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch(e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Goat>
    ): GoatRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { goat ->
                goatRemoteKeysDao.getRemoteKeys(goatId = goat.id)
            }
    }

    private suspend fun getRemoteKeysForFirstItem(
        state: PagingState<Int, Goat>
    )
            : GoatRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { goat ->
                goatRemoteKeysDao.getRemoteKeys(goatId = goat.id)
            }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(
        state: PagingState<Int, Goat>
    ): GoatRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                goatRemoteKeysDao.getRemoteKeys(goatId = id)
            }
        }
    }

//        private fun parseMillis(millis: Long): String {
//        val date = Date(millis)
//        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
//        return format.format(date)
//    }


}