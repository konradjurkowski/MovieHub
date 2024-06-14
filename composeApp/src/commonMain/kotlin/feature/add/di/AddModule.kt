package feature.add.di

import feature.add.presentation.add.AddScreenViewModel
import feature.add.presentation.search_movie.SearchMovieViewModel
import org.koin.dsl.module

val addModule = module {
    factory<AddScreenViewModel> { AddScreenViewModel() }
    factory<SearchMovieViewModel> { SearchMovieViewModel(get()) }
}
