package com.example.darren.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.darren.borutoapp.util.Constants

@Entity(tableName = Constants.GOAT_REMOTE_KEYS_DATABASE_TABLE)
data class GoatRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long? = null
)

