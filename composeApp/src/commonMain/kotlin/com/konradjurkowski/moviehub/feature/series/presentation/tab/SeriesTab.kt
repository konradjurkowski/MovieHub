package com.konradjurkowski.moviehub.feature.series.presentation.tab

import androidx.compose.runtime.Composable
import com.konradjurkowski.moviehub.core.domain.model.navigation.NavigationTab
import com.konradjurkowski.moviehub.core.domain.model.navigation.TabOptions
import com.konradjurkowski.moviehub.feature.series.presentation.series.SeriesScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_series
import moviehub.composeapp.generated.resources.ic_series_selected
import moviehub.composeapp.generated.resources.series_tab_label
import org.jetbrains.compose.resources.stringResource

object SeriesTab : NavigationTab() {

    override val route = "series"

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 3L,
            title = stringResource(Res.string.series_tab_label),
            icon = Res.drawable.ic_series,
            activeIcon = Res.drawable.ic_series_selected,
        )

    @Composable
    override fun Content() = SeriesScreen()
}
