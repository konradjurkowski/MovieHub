package feature.profile.di

import feature.profile.presentation.profile.ProfileViewModel
import org.koin.dsl.module

val profileModule = module {
    factory<ProfileViewModel> { ProfileViewModel(get(), get()) }
}
