package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.detail.presentation.RecipeDetailViewModel
import com.devspace.myapplication.detail.presentation.ui.RecipeDetailScreen
import com.devspace.myapplication.list.presentation.RecipeListViewModel
import com.devspace.myapplication.list.presentation.ui.RecipeListScreen
import com.devspace.myapplication.presentationscr.PresentationScreen
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel
import com.devspace.myapplication.search.presentation.ui.SearchRecipesScreen

@Composable
fun ERApp(
    recipeListViewModel: RecipeListViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    searchRecipeViewModel: SearchRecipeViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "presentationScreen") {
        composable(route = "presentationScreen") {
            PresentationScreen(navController)
        }
        composable(route = "recipeListScreen") {
            RecipeListScreen(
                navController,
                recipeListViewModel
                )
        }
        composable(
            route = "recipeDetailScreen" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(
                movieId,
                navController,
                recipeDetailViewModel
            )
        }
        composable(
            route = "searchRecipesScreen" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val query = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(
                navController,
                searchRecipeViewModel,
                query
            )
        }
    }
}