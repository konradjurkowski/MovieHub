package com.konradjurkowski.moviehub.feature.group.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.konradjurkowski.moviehub.feature.group.navigation.GroupDestination.CreateGroupRoute
import com.konradjurkowski.moviehub.feature.group.navigation.GroupDestination.JoinGroupRoute
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupScreen
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupScreen

fun NavGraphBuilder.addGroupGraph() {
    composable<JoinGroupRoute> { JoinGroupScreen() }
    composable<CreateGroupRoute> { CreateGroupScreen() }
}
