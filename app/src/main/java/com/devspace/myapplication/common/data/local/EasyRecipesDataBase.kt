package com.devspace.myapplication.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecipeEntity::class], version = 1)
abstract class EasyRecipesDataBase : RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao
}