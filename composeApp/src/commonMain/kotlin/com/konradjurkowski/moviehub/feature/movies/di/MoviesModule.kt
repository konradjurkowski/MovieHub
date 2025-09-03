package com.konradjurkowski.moviehub.feature.movies.di

import com.konradjurkowski.moviehub.core.utils.constants.ApiConstants
import com.konradjurkowski.moviehub.feature.movies.data.api.MovieApiImpl
import com.konradjurkowski.moviehub.feature.movies.data.repository.MovieRepositoryImpl
import com.konradjurkowski.moviehub.feature.movies.domain.api.MovieApi
import com.konradjurkowski.moviehub.feature.movies.domain.repository.MovieRepository
import com.konradjurkowski.moviehub.feature.movies.presentation.add.AddMovieViewModel
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.MoviePreviewViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val moviesModule = module {
    single<MovieApi> {
        val httpClient = get<HttpClient>(named(ApiConstants.MovieHub.NAME))
        MovieApiImpl(httpClient)
    }

    singleOf(::MovieRepositoryImpl) bind MovieRepository::class

    viewModelOf(::AddMovieViewModel)
    viewModelOf(::MoviePreviewViewModel)
}
