package core.utils

import core.di.appModule
import core.di.networkModule
import feature.auth.di.authModule
import feature.home.di.homeModule
import feature.movies.di.moviesModule
import feature.notifications.di.notificationsModule
import feature.permissions.di.permissionsModule
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
            appModule,
            networkModule,
            authModule,
            homeModule,
            moviesModule,
            profileModule,
            ratingModule,
            seriesModule,
            permissionsModule,
            notificationsModule,
        )
    }
}
