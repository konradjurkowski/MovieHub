package com.konradjurkowski.moviehub.core.di

import com.konradjurkowski.moviehub.core.data.api.ApiClientFactoryImpl
import com.konradjurkowski.moviehub.core.data.api.AuthInterceptor
import com.konradjurkowski.moviehub.core.domain.api.ApiClientFactory
import com.konradjurkowski.moviehub.core.utils.constants.ApiConstants
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    factory<ApiClientFactory> { ApiClientFactoryImpl() }

    single<HttpClient>(named(ApiConstants.Auth.NAME)) {
        val factory = get<ApiClientFactory>()
        factory.createBaseClient(ApiConstants.Auth.BASE_URL)
    }
    single<AuthInterceptor> {
        val httpClient = get<HttpClient>(named(ApiConstants.Auth.NAME))
        AuthInterceptor(authDataStore = get(), httpClient = httpClient, appNavigator = get())
    }

    single<HttpClient>(named(ApiConstants.MovieHub.NAME)) {
        val factory = get<ApiClientFactory>()
        val authInterceptor = get<AuthInterceptor>()
        factory.createBaseClient(ApiConstants.MovieHub.BASE_URL).config {
            install(authInterceptor)
        }
    }
    single<HttpClient>(named(ApiConstants.Cloudinary.NAME)) {
        val factory = get<ApiClientFactory>()
        factory.createBaseClient(ApiConstants.Cloudinary.BASE_URL)
    }
}
