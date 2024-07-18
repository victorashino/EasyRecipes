package com.devspace.myapplication.list.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.ERHtmlToText
import com.devspace.myapplication.list.presentation.RecipeListViewModel

@Composable
fun RecipeListScreen(
    navHostController: NavHostController,
    viewModel: RecipeListViewModel
) {
    RecipeListContent(
        navHostController,
        viewModel
    )
}

@Composable
private fun RecipeListContent(
    navHostController: NavHostController,
    viewModel: RecipeListViewModel
) {
    val context = LocalContext.current
    val isConnected = viewModel.isNetworkAvailable(context)

    val randomRecipes by viewModel.uiRandomRecipes.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            query,
            onSearch = {
                if (isConnected) {
                    navHostController.navigate("searchRecipesScreen/${query}")
                } else {
                    Toast.makeText(context, "Sem acesso a internet", Toast.LENGTH_SHORT).show()
                }
            },
            onQueryChange = { query = it }
            )
        RecipeList(
            recipeList = randomRecipes
        ) {
            if (isConnected) {
                navHostController.navigate(route = "recipeDetailScreen/${it.id}")
            } else {
                Toast.makeText(context, "Sem acesso a internet", Toast.LENGTH_SHORT).show()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit
) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        query = query,
        onQueryChange = { onQueryChange(it) },
        onSearch = { onSearch(query) },
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(
                text = "Search...",
                color = Color.Gray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Search Icon",
                modifier = Modifier.clickable { onQueryChange("") },
                tint = Color.Gray
            )
        },
        shape = RoundedCornerShape(16.dp)
    ) {

    }
}

@Composable
private fun RecipeList(
    recipeList: RecipeListUiState,
    onClick: (RecipeUiData) -> Unit
) {
    LazyColumn {
        items(recipeList.list) {
            RecipeItem(
                recipeDto = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecipeItem(
    recipeDto: RecipeUiData,
    onClick: (RecipeUiData) -> Unit
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
            Spacer(modifier = Modifier.size(4.dp))
            ERHtmlToText(
                text = recipeDto.summary,
                maxLine = 5
            )
        }
    }
}