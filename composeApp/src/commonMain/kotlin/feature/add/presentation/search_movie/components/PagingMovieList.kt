package feature.add.presentation.search_movie.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import core.components.loading.LoadingIndicator
import core.components.result.OngoingLoading
import core.components.media.VerticalMediaCard
import core.components.result.EmptyView
import core.components.result.FailureWidget
import core.components.result.OngoingError
import core.utils.Dimens
import core.utils.isError
import core.utils.isLoading
import feature.movies.domain.model.Movie
import feature.movies.presentation.movies.localMoviesGenresList
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.no_results
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PagingMovieList(
    modifier: Modifier = Modifier,
    pagingMovies: LazyPagingItems<Movie>,
) {
    if (!pagingMovies.loadState.refresh.isLoading() && !pagingMovies.loadState.refresh.isError() &&
        pagingMovies.itemCount == 0
    ) {
        EmptyView(
            modifier = modifier,
            message = stringResource(Res.string.no_results),
        )
    } else {
        LazyColumn(modifier = modifier) {
            items(pagingMovies.itemCount) { index ->
                pagingMovies[index]?.let { movie ->
                    VerticalMediaCard(
                        title = movie.title,
                        imageUrl = movie.posterPath ?: "",
                        rating = movie.voteAverage,
                        genres = localMoviesGenresList.filter { movie.genreIds.contains(it.id) }
                    )
                }
            }
            pagingMovies.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingIndicator(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item {
                            FailureWidget(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .padding(Dimens.regularPadding),
                                onButtonClick = { retry() }
                            )
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { OngoingLoading() }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            OngoingError { retry() }
                        }
                    }
                }
            }
        }
    }
}
