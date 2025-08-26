package com.konradjurkowski.moviehub.core.domain.model

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource

abstract class NavigationTab {
    abstract val route: String
    abstract val options: TabOptions @Composable get

    @Composable abstract fun Content()

    @Composable
    fun iconFor(selected: Boolean): DrawableResource =
        if (selected) options.activeIcon ?: options.icon else options.icon
}

data class TabOptions(
    val index: Long,
    val title: String,
    val icon: DrawableResource,
    val activeIcon: DrawableResource? = null,
)
