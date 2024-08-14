package com.devspace.myapplication.list.data.local

import com.devspace.myapplication.common.data.model.Recipe

interface LocalDataSource {

    suspend fun updateLocalItems(recipes: List<Recipe>)

    suspend fun getRecipes(): List<Recipe>

}
