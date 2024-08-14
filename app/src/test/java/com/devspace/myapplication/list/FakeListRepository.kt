package com.devspace.myapplication.list

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.list.data.ListRepository
import com.devspace.myapplication.list.data.local.LocalDataSource
import com.devspace.myapplication.list.data.remote.RemoteDataSource

class FakeListRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ListRepository {

    var recipesResult: Result<List<Recipe>?> = Result.success(emptyList())

    override suspend fun getRecipes(): Result<List<Recipe>?> = recipesResult
}