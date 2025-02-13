package feature.movies.di

import core.utils.constants.MovieApiConstants
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
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val moviesModule = module {
    single<MovieApi> { MovieApiImpl(get(named(MovieApiConstants.NAME))) }
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
    singleOf(::MovieRegistryImpl) bind MovieRegistry::class

    factoryOf(::MoviesViewModel)
    factory<MovieDetailsViewModel> { (movieId: Long) -> MovieDetailsViewModel(movieId, get(), get(), get(), get()) }
    factory<MoviePreviewViewModel> { (movieId: Long) -> MoviePreviewViewModel(movieId, get(), get(), get()) }
    factoryOf(::SearchMovieViewModel)
}
