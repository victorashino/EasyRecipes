package com.devspace.myapplication.list.data

import com.devspace.myapplication.common.model.RecipeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RandomListService {

    @GET("recipes/random?number=2")
    suspend fun getRandom(): Response<RecipeResponse>

}