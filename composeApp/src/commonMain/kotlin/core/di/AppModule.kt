package core.di

import core.tools.validator.FormValidator
import core.tools.validator.FormValidatorImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.crashlytics.crashlytics
import org.koin.dsl.module

val appModule = module {
    single { Firebase.auth }
    single { Firebase.crashlytics }
    factory<FormValidator> { FormValidatorImpl() }
}
