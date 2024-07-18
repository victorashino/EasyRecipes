package com.devspace.myapplication.common.data.remote.model

data class SearchRecipeResponse(
    val offset: Int,
    val number: Int,
    val results: List<SearchRecipeDto>,
    val totalResults: Int
)