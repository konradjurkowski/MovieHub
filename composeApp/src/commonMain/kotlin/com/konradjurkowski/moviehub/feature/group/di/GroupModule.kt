package com.konradjurkowski.moviehub.feature.group.di

import com.konradjurkowski.moviehub.core.utils.constants.ApiConstants
import com.konradjurkowski.moviehub.feature.group.data.api.GroupApiImpl
import com.konradjurkowski.moviehub.feature.group.data.repository.GroupRepositoryImpl
import com.konradjurkowski.moviehub.feature.group.domain.api.GroupApi
import com.konradjurkowski.moviehub.feature.group.domain.repository.GroupRepository
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupViewModel
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val groupModule = module {
    single<GroupApi> {
        val httpClient = get<HttpClient>(named(ApiConstants.MovieHub.NAME))
        GroupApiImpl(httpClient = httpClient)
    }
    singleOf(::GroupRepositoryImpl) bind GroupRepository::class

    viewModelOf(::JoinGroupViewModel)
    viewModelOf(::CreateGroupViewModel)
}
