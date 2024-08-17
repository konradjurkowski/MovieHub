package feature.add.di

import feature.add.presentation.add.AddScreenViewModel
import feature.add.presentation.add_movie.AddMovieViewModel
import feature.add.presentation.add_series.AddSeriesViewModel
import feature.add.presentation.search_movie.SearchMovieViewModel
import feature.add.presentation.search_series.SearchSeriesViewModel
import org.koin.dsl.module

val addModule = module {
    factory<AddScreenViewModel> { AddScreenViewModel() }
    factory<SearchMovieViewModel> { SearchMovieViewModel(get(), get(), get(), get()) }
    factory<SearchSeriesViewModel> { SearchSeriesViewModel(get()) }
    factory<AddMovieViewModel> { AddMovieViewModel(get(), get(), get()) }
    factory<AddSeriesViewModel> { AddSeriesViewModel(get(), get(), get()) }
}
