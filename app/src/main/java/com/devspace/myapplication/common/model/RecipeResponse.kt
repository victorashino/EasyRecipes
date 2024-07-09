package com.devspace.myapplication.common.model

data class RecipeResponse(
    val recipes: List<RecipeDto>
)

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String
)

data class SearchRecipeResponse(
    val offset: Int,
    val number: Int,
    val results: List<SearchRecipeDto>,
    val totalResults: Int
)

data class SearchRecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String
)