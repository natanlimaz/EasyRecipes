package com.devspace.myapplication.designsystem

import android.text.TextUtils
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.textview.MaterialTextView

@Composable
fun HtmlText(
    modifier: Modifier,
    text: String,
    maxLine: Int? = null
) {

    val spannedText = HtmlCompat.fromHtml(text, 0);
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb();

    AndroidView(
        modifier = modifier,
        factory={
            MaterialTextView(it).apply {
                setTextColor(textColor)
                textSize = 16f
                ellipsize = TextUtils.TruncateAt.END
                maxLine?.let {
                    maxLines = it
                }
            }
        },
        update = { it.text = spannedText }
    )

}