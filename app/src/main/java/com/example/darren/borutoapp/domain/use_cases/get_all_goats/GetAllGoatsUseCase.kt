package com.example.darren.borutoapp.domain.use_cases.get_all_goats

import androidx.paging.PagingData
import com.example.darren.borutoapp.data.repository.Repository
import com.example.darren.borutoapp.domain.model.Goat
import kotlinx.coroutines.flow.Flow

class GetAllGoatsUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Goat>>{
        return repository.getAllGoats()
    }
}