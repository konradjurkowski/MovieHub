package com.konradjurkowski.moviehub.core.di

import com.konradjurkowski.moviehub.core.data.storage.DataStoreFactory
import com.konradjurkowski.moviehub.core.data.storage.IosDataStoreFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val iosModule = module {
    singleOf(::IosDataStoreFactory) bind DataStoreFactory::class
}
