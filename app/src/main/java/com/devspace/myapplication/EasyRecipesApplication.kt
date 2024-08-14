package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.local.EasyRecipesDataBase
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.list.data.RecipeListRepository
import com.devspace.myapplication.list.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.list.data.remote.RandomListService
import com.devspace.myapplication.list.data.remote.RecipeListRemoteDataSource
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EasyRecipesApplication : Application()