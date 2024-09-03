package core.di

import core.tools.dispatcher.CoroutineDispatchersProviderImpl
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.validator.FormValidator
import core.tools.validator.FormValidatorImpl
import core.utils.PlatformInfo
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val appModule = module {
    single {
        val firestore = Firebase.firestore
        if (PlatformInfo.isDebug) firestore.setLoggingEnabled(true)
        firestore
    }
    single { Firebase.auth }
    single { Firebase.crashlytics }
    single { EventBus() }
    single<DispatchersProvider> { CoroutineDispatchersProviderImpl() }
    factory<FormValidator> { FormValidatorImpl() }
}
