package feature.home.di

import feature.home.presentation.home.HomeViewModel
import feature.home.presentation.main.MainScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeModule = module {
    factoryOf(::MainScreenViewModel)
    factoryOf(::HomeViewModel)
}
