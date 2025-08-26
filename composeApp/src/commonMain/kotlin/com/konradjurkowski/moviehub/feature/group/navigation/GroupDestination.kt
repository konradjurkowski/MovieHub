package com.konradjurkowski.moviehub.feature.group.navigation

import com.konradjurkowski.moviehub.core.navigation.AppDestination
import kotlinx.serialization.Serializable

sealed interface GroupDestination : AppDestination {

    @Serializable
    data object JoinGroupRoute : GroupDestination

    @Serializable
    data object CreateGroupRoute : GroupDestination
}
