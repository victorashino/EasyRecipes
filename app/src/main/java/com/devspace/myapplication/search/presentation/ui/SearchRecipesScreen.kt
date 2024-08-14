package com.devspace.myapplication.search.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.data.remote.model.SearchRecipeDto
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel

@Composable
fun SearchRecipesScreen(
    navHostController: NavHostController,
    query: String,
    viewModel: SearchRecipeViewModel = hiltViewModel()
) {
    RecipeListContent(
        navHostController,
        viewModel,
        query
    )
}

@Composable
private fun RecipeListContent(
    navHostController: NavHostController,
    viewModel: SearchRecipeViewModel,
    query: String
) {
    val searchRecipes by viewModel.uiSearchRecipes.collectAsState()
    viewModel.searchRecipes(query)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        searchRecipes?.let {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = query.replaceFirstChar { it.titlecase() }
                    )
                }
                RecipeList(
                    recipeList = it
                ) {
                    navHostController.navigate(route = "recipeDetailScreen/${it.id}")
                }
            }
        }
    }
}

@Composable
private fun RecipeList(
    recipeList: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    LazyColumn {
        items(recipeList) {
            RecipeItem(
                recipeDto = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecipeItem(
    recipeDto: SearchRecipeDto,
    onClick: (SearchRecipeDto) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick.invoke(recipeDto) }
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFFFF0E7))
                .padding(8.dp)
        ) {
            AsyncImage(
                model = recipeDto.image,
                contentDescription = "${recipeDto.title} image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = recipeDto.title,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}