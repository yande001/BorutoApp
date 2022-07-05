package com.example.darren.borutoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.darren.borutoapp.domain.model.GoatRemoteKeys
import com.example.darren.borutoapp.domain.model.HeroRemoteKeys


@Dao
interface GoatRemoteKeysDao {

    @Query("SELECT * FROM goat_remote_keys_table WHERE id = :goatId")
    suspend fun getRemoteKeys(goatId: Int): GoatRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(goatRemoteKeys: List<GoatRemoteKeys>)

    @Query("DELETE FROM goat_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}