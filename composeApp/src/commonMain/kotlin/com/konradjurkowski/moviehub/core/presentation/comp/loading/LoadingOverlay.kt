package com.konradjurkowski.moviehub.core.presentation.comp.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import moviehub.composeapp.generated.resources.Res

@Composable
fun LoadingOverlay(
    loading: Boolean = false,
    onDismiss: () -> Unit = {},
) {
    if (!loading) return

    val lottieComposition by rememberLottieComposition {
        val json = Res.readBytes("files/loading_anim.json").decodeToString()
        LottieCompositionSpec.JsonString(json)
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberLottiePainter(lottieComposition, iterations = Compottie.IterateForever),
                contentDescription = null,
            )
        }
    }
}
