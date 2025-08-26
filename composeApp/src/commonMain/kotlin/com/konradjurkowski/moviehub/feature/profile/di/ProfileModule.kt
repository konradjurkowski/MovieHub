package com.konradjurkowski.moviehub.feature.profile.di

import com.konradjurkowski.moviehub.feature.profile.presentation.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)
}
