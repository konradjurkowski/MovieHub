package org.konradjurkowski.moviehub

import android.app.Application
import core.di.initializeKoin

class MovieHubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
}
