package com.konradjurkowski.moviehub.feature.movies.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.AddMovieRoute
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.MoviePreviewRoute
import com.konradjurkowski.moviehub.feature.movies.presentation.add.AddMovieScreen
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.MoviePreviewScreen
import kotlinx.serialization.Serializable

sealed interface MoviesDestination {

    @Serializable
    data object AddMovieRoute : MoviesDestination

    @Serializable
    data class MoviePreviewRoute(val movieId: Long) : MoviesDestination
}

fun NavGraphBuilder.addMoviesGraph() {
    composable<AddMovieRoute> { AddMovieScreen() }
    composable<MoviePreviewRoute> { MoviePreviewScreen() }
}
