package com.example.darren.borutoapp.domain.use_cases.get_selected_goat

import com.example.darren.borutoapp.data.repository.Repository
import com.example.darren.borutoapp.domain.model.Goat

class GetSelectedGoatUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(goatId: Int): Goat {
        return repository.getSelectedGoat(goatId = goatId)
    }
}