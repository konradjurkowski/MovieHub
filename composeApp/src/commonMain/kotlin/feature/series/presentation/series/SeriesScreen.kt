package feature.series.presentation.series

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import core.components.media.HorizontalMediaList
import core.components.media.VerticalMediaList
import core.components.other.SmallSpacer
import core.components.text.SectionTitle
import feature.series.domain.model.Series

class SeriesScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SeriesViewModel>()
        val state by viewModel.state.collectAsState()

        state.displayResult(
            onSuccess = { data ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    SectionTitle(title = "Popular") {
                        // TODO
                    }
                    HorizontalMediaList(mediaList = data.popularSeries)
                    SmallSpacer()
                    SectionTitle(title = "Top Rated") {
                        // TODO
                    }
                    SmallSpacer()
                    VerticalMediaList(mediaList = data.topRatedSeries)
                }
            },
            onLoading = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    SectionTitle(title = "Popular") {
                        // TODO
                    }
                    HorizontalMediaList<Series>(isLoading = true)
                    SmallSpacer()
                    SectionTitle(title = "Top Rated") {
                        // TODO
                    }
                    SmallSpacer()
                    VerticalMediaList<Series>(isLoading = true)
                }
            },
            onFailure = {

            }
        )
    }
}
