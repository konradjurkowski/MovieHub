package com.konradjurkowski.moviehub.feature.movies.presentation.tab

import androidx.compose.runtime.Composable
import com.konradjurkowski.moviehub.core.domain.model.NavigationTab
import com.konradjurkowski.moviehub.core.domain.model.TabOptions
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_movies
import moviehub.composeapp.generated.resources.ic_movies_selected
import moviehub.composeapp.generated.resources.movies_tab_label
import org.jetbrains.compose.resources.stringResource

object MoviesTab : NavigationTab() {

    override val route = "movies"

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2L,
            title = stringResource(Res.string.movies_tab_label),
            icon = Res.drawable.ic_movies,
            activeIcon = Res.drawable.ic_movies_selected,
        )

    @Composable
    override fun Content() = MoviesScreen()
}
