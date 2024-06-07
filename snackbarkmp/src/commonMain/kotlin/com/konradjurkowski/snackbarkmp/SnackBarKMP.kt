package com.konradjurkowski.snackbarkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

@Composable
fun SnackBarKMP(
    modifier: Modifier = Modifier,
    snackBarData: SnackBarData,
    onCloseClick: () -> Unit = {}
) {
    val hapticFeedback = LocalHapticFeedback.current
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(snackBarData.snackBarType.color)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = snackBarData.getMessage(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {
               hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                onCloseClick.invoke()
            }
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
