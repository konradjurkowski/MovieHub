package com.konradjurkowski.moviehub.core.di

import com.konradjurkowski.moviehub.core.data.events.EventBusImpl
import com.konradjurkowski.moviehub.core.domain.events.EventBus
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateBaseUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateEmailUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidatePasswordMatchUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidatePasswordUseCase
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.navigation.AppNavigatorImpl
import com.konradjurkowski.moviehub.core.presentation.screens.main.MainViewModel
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerViewModel
import com.konradjurkowski.moviehub.core.utils.coroutines.CoroutineDispatchersProviderImpl
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<DispatchersProvider> { CoroutineDispatchersProviderImpl() }
    singleOf(::AppNavigatorImpl) bind AppNavigator::class
    singleOf(::EventBusImpl) bind EventBus::class

    factoryOf(::ValidateBaseUseCase)
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidatePasswordMatchUseCase)

    viewModelOf(::QrCodeScannerViewModel)
    viewModelOf(::MainViewModel)
}
