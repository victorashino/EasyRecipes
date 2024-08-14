package com.devspace.myapplication.list.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.EasyRecipesApplication
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.di.DispatcherIO
import com.devspace.myapplication.list.data.ListRepository
import com.devspace.myapplication.list.data.RecipeListRepository
import com.devspace.myapplication.list.data.remote.RandomListService
import com.devspace.myapplication.list.presentation.ui.RecipeListUiState
import com.devspace.myapplication.list.presentation.ui.RecipeUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: ListRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiRandomRecipes = MutableStateFlow(RecipeListUiState())
    val uiRandomRecipes: StateFlow<RecipeListUiState> = _uiRandomRecipes

    init {
        fetchRandomRecipes()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun fetchRandomRecipes() = viewModelScope.launch(dispatcher) {
        _uiRandomRecipes.value = RecipeListUiState(isLoading = true)
        val result = repository.getRecipes()
        if (result.isSuccess) {
            val recipes = result.getOrNull()
            if (recipes != null) {
                val recipeListUiState = recipes.map { recipeDto ->
                    RecipeUiData(
                        id = recipeDto.id,
                        title = recipeDto.title,
                        image = recipeDto.image,
                        summary = recipeDto.summary
                    )
                }
                _uiRandomRecipes.value = RecipeListUiState(list = recipeListUiState)
            }
        } else {
            val ex = result.exceptionOrNull()
            if (ex is UnknownHostException) {
                _uiRandomRecipes.value = RecipeListUiState(
                    isError = true,
                    messageError = "Not Internet connection"
                )
            } else {
                _uiRandomRecipes.value = RecipeListUiState(isError = true)
            }
        }
    }

/*    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return RecipeListViewModel(
                    repository = (application as EasyRecipesApplication).repository,
                    dispatcher = Dispatchers.IO
                ) as T
            }
        }
    }*/
}