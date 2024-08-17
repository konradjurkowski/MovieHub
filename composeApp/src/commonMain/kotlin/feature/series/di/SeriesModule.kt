package feature.series.di

import feature.series.data.api.SeriesApi
import feature.series.data.api.SeriesApiImpl
import feature.series.data.repository.SeriesRepository
import feature.series.data.repository.SeriesRepositoryImpl
import feature.series.presentation.series.SeriesViewModel
import feature.series.presentation.series_details.SeriesDetailsViewModel
import org.koin.dsl.module

val seriesModule = module {
    single<SeriesApi> { SeriesApiImpl(get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get(), get(), get()) }
    factory<SeriesViewModel> { SeriesViewModel(get(), get(), get()) }
    factory<SeriesDetailsViewModel> { (seriesId: Long) -> SeriesDetailsViewModel(seriesId, get(), get(), get(), get()) }
}
