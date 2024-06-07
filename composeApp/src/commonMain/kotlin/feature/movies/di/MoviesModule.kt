package feature.movies.di

import feature.movies.data.api.MovieApi
import feature.movies.data.api.MovieApiImpl
import feature.movies.data.repository.MovieRepository
import feature.movies.data.repository.MovieRepositoryImpl
import feature.movies.presentation.movies.MoviesViewModel
import org.koin.dsl.module

val moviesModule = module {
    single<MovieApi> { MovieApiImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    factory<MoviesViewModel> { MoviesViewModel(get()) }
}
