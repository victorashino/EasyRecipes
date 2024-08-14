package com.devspace.myapplication.detail

import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.detail.data.DetailService
import retrofit2.Response

class FakeDetailService : DetailService {

    var shouldReturnError = false

    override suspend fun getRecipeDetail(id: String): Response<RecipeDto> {
        return if (shouldReturnError) {
            Response.success(null)
        } else {
            val fakeRecipe = RecipeDto(
                title = "Recipe 1",
                id = 1,
                image = "image recipe 1",
                summary = "Summary recipe 1"
            )
            Response.success(fakeRecipe)
        }
    }
}