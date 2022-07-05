package com.example.darren.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.darren.borutoapp.util.Constants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Constants.GOAT_DATABASE_TABLE)
data class Goat(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val ranking: Int,
    val champ: Int,
    val mvp: Int,
    val point: Double,
    val rebound: Double,
    val assist: Double,
    val allTeam: List<String>,
    val leaders: List<String>
)
