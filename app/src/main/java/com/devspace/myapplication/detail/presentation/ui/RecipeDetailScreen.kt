package com.devspace.myapplication.detail.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.ERHtmlToText
import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.detail.presentation.RecipeDetailViewModel
import com.devspace.myapplication.ui.theme.raleway
import com.devspace.myapplication.ui.theme.urbanist

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    navHostController: NavHostController,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val recipeDetail by viewModel.uiRecipeDetail.collectAsState()
    viewModel.fetchDetail(recipeId)

    recipeDetail?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navHostController.popBackStack()
                    },
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Back Button",
                        Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "EasyRecipes",
                    Modifier.padding(top = 40.dp, start = 0.dp),
                    style = TextStyle(
                        fontFamily = raleway,
                        fontSize = 24.sp,
                        color = Color(0xFFA10C0C),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            RecipeDetailContent(it)
        }
    }
}

@Composable
private fun RecipeDetailContent(recipe: RecipeDto) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = recipe.title,
            Modifier.padding(top = 24.dp, start = 24.dp, bottom = 20.dp),
            style = TextStyle(
                fontFamily = urbanist,
                fontSize = 24.sp,
                color = Color(0xFFA10C0C),
                fontWeight = FontWeight.Medium
            )
        )

        AsyncImage(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .padding(start = 20.dp, end = 20.dp, bottom = 43.dp)
                .fillMaxSize()
                .border(
                    width = 0.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} Poster Image"
        )
        ERHtmlToText(
            text = recipe.summary
        )
    }
}