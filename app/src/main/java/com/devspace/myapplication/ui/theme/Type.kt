package com.devspace.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devspace.myapplication.R

val raleway = FontFamily(
    Font(R.font.raleway, FontWeight.Bold)
)

val urbanist = FontFamily(
    Font(R.font.urbanist_medium, FontWeight.W500),
    Font(R.font.urbanist_semibold, FontWeight.SemiBold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W500,
        fontSize = 22.sp
    )
)
