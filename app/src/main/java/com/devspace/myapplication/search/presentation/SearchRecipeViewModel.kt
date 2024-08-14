package com.devspace.myapplication.search.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.model.SearchRecipeDto
import com.devspace.myapplication.search.data.SearchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val searchService: SearchService
) : ViewModel() {

    private val _uiSearchRecipes = MutableStateFlow<List<SearchRecipeDto>?>(emptyList())
    val uiSearchRecipes: StateFlow<List<SearchRecipeDto>?> = _uiSearchRecipes

    fun searchRecipes(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = searchService.searchRecipes(query)
        if (response.isSuccessful) {
            val recipesResponse = response.body()
            if (recipesResponse != null) {
                _uiSearchRecipes.value = recipesResponse.results
                Log.d("SearchRecipeViewModel", "${recipesResponse.results}")
            }
        } else {
            Log.d("SearchRecipeViewModel", "Request Error :: ${response.errorBody()}")
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

/*    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchService = RetrofitClient.retrofitInstance.create(SearchService::class.java)
                return SearchRecipeViewModel(
                    searchService
                ) as T
            }
        }
    }*/
}
