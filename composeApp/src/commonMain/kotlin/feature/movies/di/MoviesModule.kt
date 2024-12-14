package feature.movies.di

import feature.movies.data.api.MovieApi
import feature.movies.data.api.MovieApiImpl
import feature.movies.data.repository.MovieRepository
import feature.movies.data.repository.MovieRepositoryImpl
import feature.movies.data.storage.MovieRegistry
import feature.movies.data.storage.MovieRegistryImpl
import feature.movies.presentation.details.MovieDetailsViewModel
import feature.movies.presentation.movies.MoviesViewModel
import feature.movies.presentation.preview.MoviePreviewViewModel
import feature.movies.presentation.search.SearchMovieViewModel
import org.koin.dsl.module

val moviesModule = module {
    single<MovieApi> { MovieApiImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get(), get()) }
    single<MovieRegistry> { MovieRegistryImpl() }

    factory<MoviesViewModel> { MoviesViewModel(get(), get(), get()) }
    factory<MovieDetailsViewModel> { (movieId: Long) -> MovieDetailsViewModel(movieId, get(), get(), get(), get()) }
    factory<MoviePreviewViewModel> { (movieId: Long) -> MoviePreviewViewModel(movieId, get(), get(), get(), get()) }
    factory<SearchMovieViewModel> { SearchMovieViewModel(get(), get(), get(), get(), get()) }
}
