package com.konradjurkowski.moviehub.feature.home.presentation.tab

import androidx.compose.runtime.Composable
import com.konradjurkowski.moviehub.core.domain.model.NavigationTab
import com.konradjurkowski.moviehub.core.domain.model.TabOptions
import com.konradjurkowski.moviehub.feature.home.presentation.home.HomeScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.home_tab_label
import moviehub.composeapp.generated.resources.ic_home
import moviehub.composeapp.generated.resources.ic_home_selected
import org.jetbrains.compose.resources.stringResource

object HomeTab : NavigationTab() {

    override val route = "home"

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1L,
            title = stringResource(Res.string.home_tab_label),
            icon = Res.drawable.ic_home,
            activeIcon = Res.drawable.ic_home_selected,
        )

    @Composable
    override fun Content() = HomeScreen()
}
