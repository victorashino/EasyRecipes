package com.devspace.myapplication.search.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.components.CustomSearchBar
import com.devspace.myapplication.common.data.remote.model.SearchRecipeDto
import com.devspace.myapplication.list.presentation.ui.RecipeUiData
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel
import com.devspace.myapplication.ui.theme.raleway
import com.devspace.myapplication.ui.theme.urbanist

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
    var query by remember { mutableStateOf(query) }
    val context = LocalContext.current
    val isConnected = viewModel.isNetworkAvailable(context)
    viewModel.searchRecipes(query)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1))
    ) {
        searchRecipes?.let {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navHostController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Back Button",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Text(
                            text = "EasyRecipes",
                            style = TextStyle(
                                fontFamily = raleway,
                                fontSize = 24.sp,
                                color = Color(0xFFA10C0C),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    CustomSearchBar(
                        query = query,
                        onSearch = {
                            if (isConnected) {
                                navHostController.navigate("searchRecipesScreen/${query}")
                            } else {
                                Toast.makeText(context, "Sem acesso a internet", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onQueryChange = { query = it }
                    )
                }
                items(it) { recipe ->
                    RecipeItem(
                        recipeDto = recipe,
                        onClick = {
                            if (isConnected) {
                                navHostController.navigate(route = "recipeDetailScreen/${recipe.id}")
                            } else {
                                Toast.makeText(context, "Sem acesso a internet", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
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
            .clickable { onClick.invoke(recipeDto) }
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 22.dp)
            .shadow( elevation = 4.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color(0x1A000000)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF1F1F1))
                .padding(0.dp)
        ) {
            AsyncImage(
                model = recipeDto.image,
                contentDescription = "${recipeDto.title} image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Text(
                text = recipeDto.title,
                modifier = Modifier.padding(start = 18.dp, top = 18.dp, bottom = 18.dp),
                fontFamily = urbanist,
                fontWeight = FontWeight.Medium
            )
        }
    }
}