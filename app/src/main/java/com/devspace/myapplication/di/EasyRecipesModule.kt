package com.devspace.myapplication.di

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.local.EasyRecipesDataBase
import com.devspace.myapplication.common.data.local.RecipeDao
import com.devspace.myapplication.common.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class EasyRecipesModule {

    @Provides
    fun provideERDataBase(application: Application): EasyRecipesDataBase {
        return Room.databaseBuilder(
            application.applicationContext,
            EasyRecipesDataBase::class.java, "database-easy-recipes"
        ).build()
    }

    @Provides
    fun provideRecipeDao(roomDataBase: EasyRecipesDataBase): RecipeDao {
        return roomDataBase.getRecipeDao()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitClient.retrofitInstance
    }

    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}