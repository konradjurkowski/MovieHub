package feature.series.di

import core.utils.constants.MovieApiConstants
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
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val seriesModule = module {
    single<SeriesApi> { SeriesApiImpl(get(named(MovieApiConstants.NAME))) }
    singleOf(::SeriesRepositoryImpl) bind SeriesRepository::class
    singleOf(::SeriesRegistryImpl) bind SeriesRegistry::class

    factoryOf(::SeriesViewModel)
    factory<SeriesDetailsViewModel> { (seriesId: Long) -> SeriesDetailsViewModel(seriesId, get(), get(), get(), get()) }
    factory<SeriesPreviewViewModel> { (seriesId: Long) -> SeriesPreviewViewModel(seriesId, get(), get(), get()) }
    factoryOf(::SearchSeriesViewModel)
}
