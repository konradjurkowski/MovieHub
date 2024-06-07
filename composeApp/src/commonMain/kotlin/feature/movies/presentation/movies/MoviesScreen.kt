package feature.movies.presentation.movies

import TestScreen
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.components.button.PrimaryButton
import core.components.media.HorizontalMediaList
import core.components.media.VerticalMediaList
import core.components.other.SmallSpacer
import core.components.text.SectionTitle
import core.utils.Resource
import feature.movies.domain.model.Movie

class MoviesScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MoviesViewModel>()
        val state by viewModel.state.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        when (val screenState = state) {
            is Resource.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    SectionTitle(title = "Popular") {
                        // TODO
                    }
                    HorizontalMediaList(mediaList = screenState.data.popularMovies)
                    SmallSpacer()
                    SectionTitle(title = "Top Rated") {
                        // TODO
                    }
                    SmallSpacer()
                    VerticalMediaList(mediaList = screenState.data.topRatedMovies)
                    PrimaryButton(text = "ASDA") {
                        navigator.push(TestScreen())
                    }
                }
            }
            Resource.Idle, Resource.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    SectionTitle(title = "Popular") {
                        // TODO
                    }
                    HorizontalMediaList<Movie>(isLoading = true)
                    SmallSpacer()
                    SectionTitle(title = "Top Rated") {
                        // TODO
                    }
                    SmallSpacer()
                    VerticalMediaList<Movie>(isLoading = true)
                }
            }
            is Resource.Failure -> {
                // TODO
            }
        }
    }
}
