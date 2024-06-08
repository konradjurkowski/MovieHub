package core.utils

import core.di.appModule
import core.di.networkModule
import feature.auth.di.authModule
import feature.movies.di.moviesModule
import feature.series.di.seriesModule
import org.koin.core.context.startKoin

fun initKoin() {
    initializeKoin()
}

fun initializeKoin() {
    startKoin {
        modules(
            appModule,
            networkModule,
            authModule,
            moviesModule,
            seriesModule,
        )
    }
}
