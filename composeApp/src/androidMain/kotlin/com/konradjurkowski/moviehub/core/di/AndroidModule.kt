package com.konradjurkowski.moviehub.core.di

import com.konradjurkowski.moviehub.core.data.storage.DataStoreFactory
import com.konradjurkowski.moviehub.core.data.storage.AndroidDataStoreFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule = module {
    singleOf(::AndroidDataStoreFactory) bind DataStoreFactory::class
}
