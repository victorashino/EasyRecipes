package com.devspace.myapplication.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun CustomSearchBar(
    query: String,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit
) {
    var isActive by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 21.dp, start = 21.dp, bottom = 16.dp)
            .height(60.dp)
            .border(
                width = 2.dp,
                color = Color(0xFFA10C0C),
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color(0xFFF1F1F1))
            .clickable { isActive = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color(0xFFA10C0C),
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(query)
                    }
                ),
                decorationBox = { innerTextField ->
                    innerTextField()
                }
            )

            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    modifier = Modifier
                        .clickable {
                            onQueryChange("")
                            isActive = false
                        },
                    tint = Color(0xFFA10C0C),
                )
            }
        }
    }
}
