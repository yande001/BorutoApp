package com.example.darren.borutoapp.domain.use_cases.get_selected_hero

import com.example.darren.borutoapp.data.repository.Repository
import com.example.darren.borutoapp.domain.model.Hero

class GetSelectedHeroUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(heroId: Int): Hero {
        return repository.getSelectedHero(heroId = heroId)
    }
}