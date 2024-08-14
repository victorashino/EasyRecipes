package com.devspace.myapplication.list

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.list.data.local.LocalDataSource

class FakeRecipeListLocalDataSource : LocalDataSource {

    var recipes = listOf<Recipe>()
    var updateItems = listOf<Recipe>()

    override suspend fun updateLocalItems(recipes: List<Recipe>) {
        updateItems = recipes
    }

    override suspend fun getRecipes(): List<Recipe> {
        return recipes
    }
}