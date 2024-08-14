package com.devspace.myapplication.detail.di

import com.devspace.myapplication.detail.data.DetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class RecipeDetailModule {

    @Provides
    fun provideMovieDetailService(retrofit: Retrofit): DetailService {
        return retrofit.create(DetailService::class.java)
    }
}