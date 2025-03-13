package feature.rating.di

import feature.rating.presentation.add_rating.AddRatingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val ratingModule = module {
    factoryOf(::AddRatingViewModel)
}
