package feature.series.di

import feature.series.data.api.SeriesApi
import feature.series.data.api.SeriesApiImpl
import feature.series.data.repository.SeriesRepository
import feature.series.data.repository.SeriesRepositoryImpl
import feature.series.presentation.series.SeriesViewModel
import org.koin.dsl.module

val seriesModule = module {
    single<SeriesApi> { SeriesApiImpl(get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get()) }
    factory<SeriesViewModel> { SeriesViewModel(get()) }
}
