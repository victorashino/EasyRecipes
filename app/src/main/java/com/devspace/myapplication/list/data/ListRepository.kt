package com.devspace.myapplication.list.data

import com.devspace.myapplication.common.data.model.Recipe

interface ListRepository {

    suspend fun getRecipes(): Result<List<Recipe>?>

}