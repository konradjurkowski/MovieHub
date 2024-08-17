package com.konradjurkowski.snackbarkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SnackBarKMP(
    modifier: Modifier = Modifier,
    snackBarData: SnackBarData,
    onCloseClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(snackBarData.snackBarType.color)
            .padding(top = getStatusBarHeight())
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = snackBarData.getMessage(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = onCloseClick,
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun getStatusBarHeight(): Dp {
    val insets = WindowInsets.systemBars
    val density = LocalDensity.current
    return with(density) { insets.getTop(density).toDp() }
}
