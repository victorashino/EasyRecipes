package com.devspace.myapplication.list.data.remote

import com.devspace.myapplication.common.data.remote.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RandomListService {

    @GET("recipes/random?number=1")
    suspend fun getRandom(): Response<RecipeResponse>

}