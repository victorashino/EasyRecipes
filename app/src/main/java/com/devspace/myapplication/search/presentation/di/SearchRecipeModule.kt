package com.devspace.myapplication.search.presentation.di

import com.devspace.myapplication.search.data.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class SearchRecipeModule {

    @Provides
    fun provideListService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }
}