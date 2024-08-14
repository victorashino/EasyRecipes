package com.devspace.myapplication.list.data.remote

import com.devspace.myapplication.common.data.model.Recipe

interface RemoteDataSource {

    suspend fun getRecipes(): Result<List<Recipe>?>

}