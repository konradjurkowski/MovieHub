package feature.profile.di

import feature.profile.presentation.profile.ProfileViewModel
import feature.profile.presentation.profile_edit.ProfileEditViewModel
import org.koin.dsl.module

val profileModule = module {
    factory<ProfileViewModel> { ProfileViewModel(get(), get()) }
    factory<ProfileEditViewModel> { ProfileEditViewModel(get(), get(), get()) }
}
