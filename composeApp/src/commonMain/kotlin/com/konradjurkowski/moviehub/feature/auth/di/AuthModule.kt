package com.konradjurkowski.moviehub.feature.auth.di

import com.konradjurkowski.moviehub.core.utils.constants.ApiConstants
import com.konradjurkowski.moviehub.feature.auth.data.api.AuthApiImpl
import com.konradjurkowski.moviehub.feature.auth.data.api.UserApiImpl
import com.konradjurkowski.moviehub.feature.auth.data.repository.AuthRepositoryImpl
import com.konradjurkowski.moviehub.feature.auth.data.storage.AuthDataStoreImpl
import com.konradjurkowski.moviehub.feature.auth.domain.api.AuthApi
import com.konradjurkowski.moviehub.feature.auth.domain.api.UserApi
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.domain.storage.AuthDataStore
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ActivateAccountViewModel
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginViewModel
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.NotificationPermissionViewModel
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterViewModel
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.SplashViewModel
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.WelcomeViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    single<AuthApi> {
        val httpClient = get<HttpClient>(named(ApiConstants.MovieHub.NAME))
        AuthApiImpl(httpClient = httpClient)
    }
    single<UserApi> {
        val httpClient = get<HttpClient>(named(ApiConstants.MovieHub.NAME))
        UserApiImpl(httpClient = httpClient)
    }
    singleOf(::AuthDataStoreImpl) bind AuthDataStore::class
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class

    viewModelOf(::SplashViewModel)
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ActivateAccountViewModel)
    viewModelOf(::NotificationPermissionViewModel)
}
