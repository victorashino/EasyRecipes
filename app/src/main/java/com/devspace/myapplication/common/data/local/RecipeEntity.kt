package com.devspace.myapplication.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val summary: String
)
