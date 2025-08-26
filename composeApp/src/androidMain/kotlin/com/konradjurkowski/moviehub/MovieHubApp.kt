package com.konradjurkowski.moviehub

import android.app.Application
import com.konradjurkowski.moviehub.core.di.androidModule
import com.konradjurkowski.moviehub.core.di.appModule
import com.konradjurkowski.moviehub.core.di.networkModule
import com.konradjurkowski.moviehub.feature.auth.di.authModule
import com.konradjurkowski.moviehub.feature.group.di.groupModule
import com.konradjurkowski.moviehub.feature.profile.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieHubApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@MovieHubApp)
            modules(
                androidModule,
                networkModule,
                appModule,
                authModule,
                groupModule,
                profileModule,
            )
        }
    }
}
