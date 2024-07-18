package com.devspace.myapplication.list.data.remote

import android.accounts.NetworkErrorException
import com.devspace.myapplication.common.data.model.Recipe

class RecipeListRemoteDataSource(
    private val listService: RandomListService
) {
    suspend fun getRecipes(): Result<List<Recipe>?> {
        return try {
            val response = listService.getRandom()
            if (response.isSuccessful) {
                val recipes = response.body()?.recipes?.map {
                    Recipe(
                        id = it.id,
                        title = it.title,
                        image = it.image,
                        summary = it.summary
                    )
                }
                Result.success(recipes)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}