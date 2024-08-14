package com.devspace.myapplication.list.presentation.di

import com.devspace.myapplication.list.data.ListRepository
import com.devspace.myapplication.list.data.RecipeListRepository
import com.devspace.myapplication.list.data.local.LocalDataSource
import com.devspace.myapplication.list.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.list.data.remote.RandomListService
import com.devspace.myapplication.list.data.remote.RecipeListRemoteDataSource
import com.devspace.myapplication.list.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class RecipeListModule {

    @Provides
    fun provideListService(retrofit: Retrofit): RandomListService {
        return retrofit.create(RandomListService::class.java)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface RecipeListModuleBinding {
    @Binds
    fun bindLocalDataSource(impl: RecipeListLocalDataSource): LocalDataSource

    @Binds
    fun bindRemoteDataSource(impl: RecipeListRemoteDataSource): RemoteDataSource

    @Binds
    fun bindListRepository(impl: RecipeListRepository): ListRepository
}