package core.utils

import core.di.appModule
import core.di.networkModule
import feature.add.di.addModule
import feature.auth.di.authModule
import feature.home.di.homeModule
import feature.movies.di.moviesModule
import feature.profile.di.profileModule
import feature.rating.di.ratingModule
import feature.series.di.seriesModule
import org.koin.core.context.startKoin

fun initKoin() {
    initializeKoin()
}

fun initializeKoin() {
    startKoin {
        modules(
            addModule,
            appModule,
            networkModule,
            authModule,
            homeModule,
            moviesModule,
            profileModule,
            ratingModule,
            seriesModule,
        )
    }
}
