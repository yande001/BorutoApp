package com.example.darren.borutoapp.domain.use_cases.read_onboarding

import com.example.darren.borutoapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Boolean>{
        return repository.readOnBoardingState()
    }
}