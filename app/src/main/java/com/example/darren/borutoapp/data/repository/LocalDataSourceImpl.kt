package com.example.darren.borutoapp.data.repository

import com.example.darren.borutoapp.data.local.BorutoDatabase
import com.example.darren.borutoapp.data.local.GoatDatabase
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero
import com.example.darren.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    borutoDatabase: BorutoDatabase,
    goatDatabase: GoatDatabase
): LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()
    private val goatDao = goatDatabase.goatDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }

    override suspend fun getSelectedGoat(goatId: Int): Goat {
        return goatDao.getSelectedGoat(goatId = goatId)
    }
}