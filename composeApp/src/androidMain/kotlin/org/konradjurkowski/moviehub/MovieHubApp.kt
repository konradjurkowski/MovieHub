package org.konradjurkowski.moviehub

import android.app.Application
import core.di.appModule
import core.di.networkModule
import feature.add.di.addModule
import feature.auth.di.authModule
import feature.home.di.homeModule
import feature.movies.di.moviesModule
import feature.rating.di.ratingModule
import feature.series.di.seriesModule
import org.koin.core.context.startKoin

class MovieHubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
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
            seriesModule,
            ratingModule,
        )
    }
}
