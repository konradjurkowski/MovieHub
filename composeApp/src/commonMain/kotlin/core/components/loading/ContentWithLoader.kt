package core.components.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.theme.withA20
import core.utils.Dimens

@Composable
fun rememberLoaderState(): LoaderState {
    return remember { LoaderState() }
}

@Composable
fun ContentWithLoader(
    loaderState: LoaderState,
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
    }
    LoaderComponent(loaderState = loaderState.isVisible)
}

@Composable
fun LoaderComponent(loaderState: Boolean) {
    if (loaderState) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier.padding(Dimens.padding24),
                color = MaterialTheme.colorScheme.onBackground.withA20(),
                shape = RoundedCornerShape(Dimens.radius16),
            ) {
                LoadingIndicator(
                    modifier = Modifier.padding(Dimens.padding24),
                    size = Dimens.smallLoadingSize,
                )
            }
        }
    }
}

class LoaderState {
    var isVisible by mutableStateOf(false)

    fun showLoader() {
        isVisible = true
    }

    fun hideLoader() {
        isVisible = false
    }
}
