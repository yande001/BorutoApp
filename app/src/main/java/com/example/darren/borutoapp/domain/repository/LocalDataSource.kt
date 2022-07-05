package com.example.darren.borutoapp.domain.repository

import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
    suspend fun getSelectedGoat(goatId: Int): Goat
}