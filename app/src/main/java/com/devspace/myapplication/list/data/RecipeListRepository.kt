package com.devspace.myapplication.list.data

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.list.data.local.LocalDataSource
import com.devspace.myapplication.list.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.list.data.remote.RecipeListRemoteDataSource
import com.devspace.myapplication.list.data.remote.RemoteDataSource
import javax.inject.Inject

class RecipeListRepository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ListRepository {
    override suspend fun getRecipes(): Result<List<Recipe>?> {
        return try {
            val result = remote.getRecipes()
            if (result.isSuccess) {
                val recipesRemote = result.getOrNull() ?: emptyList()
                if (recipesRemote.isNotEmpty()) {
                    local.updateLocalItems(recipesRemote)
                }
                return Result.success(local.getRecipes())
            } else {
                val localData = local.getRecipes()
                if (localData.isEmpty()) {
                    return result
                } else {
                    Result.success(localData)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}