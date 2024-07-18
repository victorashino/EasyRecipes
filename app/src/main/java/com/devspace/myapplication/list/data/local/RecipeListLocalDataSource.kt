package com.devspace.myapplication.list.data.local

import com.devspace.myapplication.common.data.local.RecipeDao
import com.devspace.myapplication.common.data.local.RecipeEntity
import com.devspace.myapplication.common.data.model.Recipe

class RecipeListLocalDataSource(
    private val dao: RecipeDao
) {

    suspend fun updateLocalItems(recipes: List<Recipe>) {
        val recipes = recipes.map {
            RecipeEntity(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary
            )
        }
        dao.insertAll(recipes)
    }

    suspend fun getRecipes(): List<Recipe> {
        val entities = dao.getAllRecipes()
        return entities.map {
            Recipe(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary
            )
        }
    }
}