package com.example.darren.borutoapp.domain.use_cases

import com.example.darren.borutoapp.domain.use_cases.get_all_goats.GetAllGoatsUseCase
import com.example.darren.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.darren.borutoapp.domain.use_cases.get_selected_goat.GetSelectedGoatUseCase
import com.example.darren.borutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.example.darren.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.darren.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.darren.borutoapp.domain.use_cases.search_goats.SearchGoatsUseCase
import com.example.darren.borutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val searchHeroesUseCase: SearchHeroesUseCase,
    val getSelectedHeroUseCase: GetSelectedHeroUseCase,
    val getAllGoatsUseCase: GetAllGoatsUseCase,
    val searchGoatsUseCase: SearchGoatsUseCase,
    val getSelectedGoatUseCase: GetSelectedGoatUseCase
)
