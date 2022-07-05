package com.example.darren.borutoapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero

@Dao
interface GoatDao {

    @Query("SELECT * FROM goat_table ORDER BY id ASC")
    fun getAllGoats(): PagingSource<Int, Goat>

    @Query("SELECT * FROM goat_table WHERE id= :goatId")
    fun  getSelectedGoat(goatId: Int): Goat

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGoats(goats: List<Goat>)

    @Query("DELETE FROM goat_table")
    suspend fun deleteAllGoats()
}