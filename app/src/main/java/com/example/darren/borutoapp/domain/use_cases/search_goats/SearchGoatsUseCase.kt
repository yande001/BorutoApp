package com.example.darren.borutoapp.domain.use_cases.search_goats

import androidx.paging.PagingData
import com.example.darren.borutoapp.data.repository.Repository
import com.example.darren.borutoapp.domain.model.Goat
import kotlinx.coroutines.flow.Flow

class SearchGoatsUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<Goat>>{
        return repository.searchGoats(query = query)
    }
}