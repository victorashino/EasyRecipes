package com.devspace.myapplication.list.presentation.ui

data class RecipeListUiState(
    val list: List<RecipeUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val messageError: String = "Something went wrong"
)

data class RecipeUiData(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String

)