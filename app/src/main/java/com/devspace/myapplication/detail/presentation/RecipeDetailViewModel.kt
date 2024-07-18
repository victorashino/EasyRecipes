package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uiRecipeDetail = MutableStateFlow<RecipeDto?>(null)
    val uiRecipeDetail: StateFlow<RecipeDto?> = _uiRecipeDetail

    fun fetchDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = detailService.getRecipeDetail(id)
        if (response.isSuccessful) {
            val recipes = response.body()
            if (recipes != null) {
                _uiRecipeDetail.value = recipes
                Log.d("RecipeDetailViewModel", "${response.body()}")
            }
        } else {
            Log.d("RecipeDetailViewModel", "Request Error :: ${response.errorBody()}")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService = RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return RecipeDetailViewModel(
                    detailService
                ) as T
            }
        }
    }

}