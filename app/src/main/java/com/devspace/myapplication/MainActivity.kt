package com.devspace.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.devspace.myapplication.detail.presentation.RecipeDetailViewModel
import com.devspace.myapplication.list.presentation.RecipeListViewModel
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme

class MainActivity : ComponentActivity() {

    private val listViewModel by viewModels<RecipeListViewModel>{ RecipeListViewModel.Factory }
    private val detailViewModel by viewModels<RecipeDetailViewModel>{ RecipeDetailViewModel.Factory }
    private val searchViewModel by viewModels<SearchRecipeViewModel>{ SearchRecipeViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFF6F1)
                ) {
                    ERApp(
                        listViewModel,
                        detailViewModel,
                        searchViewModel
                    )
                }
            }
        }
    }
}