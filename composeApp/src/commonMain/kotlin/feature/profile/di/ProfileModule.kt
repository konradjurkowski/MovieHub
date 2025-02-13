package feature.profile.di

import feature.profile.presentation.profile.ProfileViewModel
import feature.profile.presentation.profile_edit.ProfileEditViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val profileModule = module {
    factoryOf(::ProfileViewModel)
    factoryOf(::ProfileEditViewModel)
}
