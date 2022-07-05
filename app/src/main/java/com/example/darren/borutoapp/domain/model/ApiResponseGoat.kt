package com.example.darren.borutoapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseGoat(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val goats: List<Goat> = emptyList(),
    val lastUpdated: Long? = null
)