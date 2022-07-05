package com.example.darren.borutoapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.darren.borutoapp.data.remote.BorutoApi
import com.example.darren.borutoapp.data.remote.GoatApi
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero
import javax.inject.Inject

class SearchGoatsSource @Inject constructor(
    private val goatApi: GoatApi,
    private val query: String
): PagingSource<Int, Goat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Goat> {
        return try{
            val apiResponse = goatApi.searchGoats(name = query)
            val goats = apiResponse.goats
            if (goats.isNotEmpty()){
                LoadResult.Page(
                    data = goats,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else{
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Goat>): Int? {
        return state.anchorPosition
    }
}