package feature.series.presentation.preview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.AddMediaButton
import core.components.media.details.MediaDetailsFailure
import core.components.media.details.MediaDetailsLoading
import feature.series.presentation.preview.SeriesPreviewIntent
import feature.series.presentation.preview.SeriesPreviewIntent.BackPressed
import feature.series.presentation.preview.SeriesPreviewIntent.SeriesAddPressed
import feature.series.presentation.preview.SeriesPreviewIntent.Refresh
import feature.series.presentation.preview.SeriesPreviewIntent.VideoPressed
import feature.series.presentation.preview.SeriesPreviewState
import feature.series.presentation.preview.SeriesPreviewState.Idle
import feature.series.presentation.preview.SeriesPreviewState.Loading
import feature.series.presentation.preview.SeriesPreviewState.Success
import feature.series.presentation.preview.SeriesPreviewState.Error
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.series_screen_preview_add_series
import moviehub.composeapp.generated.resources.series_screen_preview_series_added
import org.jetbrains.compose.resources.stringResource

@Composable
fun SeriesPreviewScreen(
    state: SeriesPreviewState,
    onIntent: (SeriesPreviewIntent) -> Unit,
) {
   Scaffold(
       bottomBar = {
           AddMediaButton(
               addedTitle = stringResource(Res.string.series_screen_preview_series_added),
               notAddedTitle = stringResource(Res.string.series_screen_preview_add_series),
               isVisible = state.isSuccess(),
               isAdded = state.isMediaAdded(),
               onClick = {
                   val series = state.getSuccess()?.series
                   if (series != null) onIntent(SeriesAddPressed(series))
               },
           )
       },
   ) { contentPadding ->
       Box(
           modifier = Modifier
               .padding(bottom = contentPadding.calculateBottomPadding())
               .fillMaxSize(),
       ) {
           when (state) {
               is Success -> {
                   SeriesPreviewSuccess(
                       series = state.series,
                       castData = state.castData,
                       onBackPressed = { onIntent(BackPressed) },
                       onVideoPressed = { onIntent(VideoPressed(it)) },
                   )
               }

               Idle, Loading -> MediaDetailsLoading { onIntent(BackPressed) }

               is Error -> {
                   MediaDetailsFailure(
                       onRefresh = { onIntent(Refresh) },
                       onBackPressed = { onIntent(BackPressed) },
                   )
               }
           }
       }
   }
}
