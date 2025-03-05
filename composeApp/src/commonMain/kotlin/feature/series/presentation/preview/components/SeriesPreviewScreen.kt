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
import feature.series.presentation.preview.SeriesPreviewState
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
                   if (series != null) onIntent(SeriesPreviewIntent.SeriesAddPressed(series))
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
               is SeriesPreviewState.Success -> {
                   SeriesPreviewSuccess(
                       series = state.series,
                       castData = state.castData,
                       onBackPressed = { onIntent(SeriesPreviewIntent.BackPressed) },
                   )
               }

               SeriesPreviewState.Idle, SeriesPreviewState.Loading -> MediaDetailsLoading { onIntent(SeriesPreviewIntent.BackPressed) }

               is SeriesPreviewState.Error -> {
                   MediaDetailsFailure(
                       onRefresh = { onIntent(SeriesPreviewIntent.Refresh) },
                       onBackPressed = { onIntent(SeriesPreviewIntent.BackPressed) },
                   )
               }
           }
       }
   }
}
