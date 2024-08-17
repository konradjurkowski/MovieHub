package feature.rating.di

import feature.rating.presentation.add_rating.AddRatingViewModel
import org.koin.dsl.module

val ratingModule = module {
    factory<AddRatingViewModel> { (mediaId: Long) -> AddRatingViewModel(mediaId, get(), get(), get(), get()) }
}
