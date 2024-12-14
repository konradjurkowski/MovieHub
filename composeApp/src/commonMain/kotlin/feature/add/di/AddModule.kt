package feature.add.di

import feature.add.presentation.add.AddScreenViewModel
import org.koin.dsl.module

val addModule = module {
    factory<AddScreenViewModel> { AddScreenViewModel() }
}
