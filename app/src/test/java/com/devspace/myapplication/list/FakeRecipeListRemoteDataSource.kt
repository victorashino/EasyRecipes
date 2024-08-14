package com.devspace.myapplication.list

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.list.data.remote.RemoteDataSource

class FakeRecipeListRemoteDataSource : RemoteDataSource {

    var recipes = listOf<Recipe>()

    var shouldReturnError = false
    var exceptionToThrow: Exception? = null

    override suspend fun getRecipes(): Result<List<Recipe>?> {
        return if (!shouldReturnError) {
            Result.success(recipes)
        } else {
            Result.failure(exceptionToThrow ?: Exception("Something went wrong"))
        }
    }
}