package feature.home.di

import feature.home.presentation.home.HomeViewModel
import feature.home.presentation.main.MainScreenViewModel
import org.koin.dsl.module

val homeModule = module {
    factory<MainScreenViewModel> { MainScreenViewModel(get()) }
    factory<HomeViewModel> { HomeViewModel(get(), get(), get()) }
}
