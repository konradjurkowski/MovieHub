package core.di

import core.tools.validator.FormValidator
import core.tools.validator.FormValidatorImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.crashlytics.crashlytics
import feature.auth.di.authModule
import feature.movies.di.moviesModule
import feature.series.di.seriesModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { Firebase.auth }
    single { Firebase.crashlytics }
    factory<FormValidator> { FormValidatorImpl() }
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
