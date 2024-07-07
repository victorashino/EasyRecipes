package com.devspace.myapplication.list.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.ERHtmlToText
import com.devspace.myapplication.common.model.RecipeDto
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
    val randomRecipes by viewModel.uiRandomRecipes.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            ""
        ) {

        }
        RecipeList(
            recipeList = randomRecipes
        ) {
            navHostController.navigate(route = "recipeDetailScreen/${it.id}")
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val shape = RoundedCornerShape(16.dp)

    TextField(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.White, shape)
            .border(1.dp, Color.Gray, shape),
        shape = shape,
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(text = "Search...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
private fun RecipeList(
    recipeList: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
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
    recipeDto: RecipeDto,
    onClick: (RecipeDto) -> Unit
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