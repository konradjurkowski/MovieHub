package feature.series.di

import feature.series.data.api.SeriesApi
import feature.series.data.api.SeriesApiImpl
import feature.series.data.repository.SeriesRepository
import feature.series.data.repository.SeriesRepositoryImpl
import feature.series.data.storage.SeriesRegistry
import feature.series.data.storage.SeriesRegistryImpl
import feature.series.presentation.series.SeriesViewModel
import feature.series.presentation.details.SeriesDetailsViewModel
import feature.series.presentation.preview.SeriesPreviewViewModel
import feature.series.presentation.search.SearchSeriesViewModel
import org.koin.dsl.module

val seriesModule = module {
    single<SeriesApi> { SeriesApiImpl(get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get(), get(), get(), get()) }
    single<SeriesRegistry> { SeriesRegistryImpl() }

    factory<SeriesViewModel> { SeriesViewModel(get(), get()) }
    factory<SeriesDetailsViewModel> { (seriesId: Long) -> SeriesDetailsViewModel(seriesId, get(), get(), get(), get()) }
    factory<SeriesPreviewViewModel> { (seriesId: Long) -> SeriesPreviewViewModel(seriesId, get(), get(), get()) }
    factory<SearchSeriesViewModel> { SearchSeriesViewModel(get(), get(), get(), get()) }
}
