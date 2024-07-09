package com.devspace.myapplication.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.common.model.SearchRecipeResponse
import com.devspace.myapplication.list.data.RandomListService
import com.devspace.myapplication.search.data.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val listService: RandomListService
) : ViewModel() {

    private val _uiRandomRecipes = MutableStateFlow<List<RecipeDto>>(emptyList())
    val uiRandomRecipes: StateFlow<List<RecipeDto>> = _uiRandomRecipes

    init {
        fetchRandomRecipes()
    }

    private fun fetchRandomRecipes() = viewModelScope.launch(Dispatchers.IO) {
        val response = listService.getRandom()
        if (response.isSuccessful) {
            val recipes = response.body()?.recipes
            if (recipes != null) {
                _uiRandomRecipes.value = recipes
                Log.d("RecipeListViewModel", "${response.body()}")
            }
        } else {
            Log.d("RecipeListViewModel", "Request Error :: ${response.errorBody()}")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(RandomListService::class.java)
                return RecipeListViewModel(
                    listService
                ) as T
            }
        }
    }
}