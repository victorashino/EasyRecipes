package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.local.EasyRecipesDataBase
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.list.data.RecipeListRepository
import com.devspace.myapplication.list.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.list.data.remote.RandomListService
import com.devspace.myapplication.list.data.remote.RecipeListRemoteDataSource

class EasyRecipesApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EasyRecipesDataBase::class.java, "database-easy-recipes"
        ).build()
    }

    private val listService by lazy {
        RetrofitClient.retrofitInstance.create(RandomListService::class.java)
    }

    private val localDataSource: RecipeListLocalDataSource by lazy {
        RecipeListLocalDataSource(db.getRecipeDao())
    }

    private val remoteDataSource: RecipeListRemoteDataSource by lazy {
        RecipeListRemoteDataSource(listService)
    }

    val repository: RecipeListRepository by lazy {
        RecipeListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }
}