package com.devspace.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.google.android.material.textview.MaterialTextView

@Composable
fun ERHtmlToText(
    text: String,
    maxLine: Int? = null,
    fontSize: Float = 16f
) {
    val spannedText = HtmlCompat.fromHtml(text, 0)
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

    AndroidView(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        factory = {
            MaterialTextView(it).apply {
                setTextColor(textColor)
                setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, fontSize)
                maxLine?.let {
                    maxLines = it
                }
                typeface = ResourcesCompat.getFont(it, R.font.urbanist_medium)
            }
        },
        update = { it.text = spannedText }
    )

}