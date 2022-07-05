package com.example.darren.borutoapp.domain.use_cases.search_heroes

import android.util.Log
import androidx.paging.PagingData
import com.example.darren.borutoapp.data.repository.Repository
import com.example.darren.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class SearchHeroesUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<Hero>>{
        return repository.searchHeroes(query = query)
    }
}