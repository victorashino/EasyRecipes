package com.devspace.myapplication.presentationscr

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devspace.myapplication.R

@Composable
fun PresentationScreen(navController: NavHostController) {
    PresentationContent(navController)
}

@Composable
private fun PresentationContent(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.onboarding),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x99000000)),
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = "Let's",
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    text = "Cooking",
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    text = "Find best recipes for cooking",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
                ButtonStart {
                    navController.navigate("recipeListScreen")
                }
            }
        }
    }
}

@Composable
private fun ButtonStart(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(74.dp)
            .width(132.dp)
            .padding(top = 32.dp)
    ) {
        Text(
            text = "Start cooking",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
        )
    }
}