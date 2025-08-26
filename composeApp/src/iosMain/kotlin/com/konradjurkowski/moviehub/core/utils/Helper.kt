package com.konradjurkowski.moviehub.core.utils

import com.konradjurkowski.moviehub.core.di.appModule
import com.konradjurkowski.moviehub.core.di.iosModule
import com.konradjurkowski.moviehub.core.di.networkModule
import com.konradjurkowski.moviehub.feature.auth.di.authModule
import com.konradjurkowski.moviehub.feature.group.di.groupModule
import org.koin.core.context.startKoin

fun initKoin() {
    initializeKoin()
}

fun initializeKoin() {
    startKoin {
        modules(
            iosModule,
            networkModule,
            appModule,
            authModule,
            groupModule,
        )
    }
}
