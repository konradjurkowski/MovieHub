package com.konradjurkowski.moviehub.feature.profile.presentation.tab

import androidx.compose.runtime.Composable
import com.konradjurkowski.moviehub.core.domain.model.navigation.NavigationTab
import com.konradjurkowski.moviehub.core.domain.model.navigation.TabOptions
import com.konradjurkowski.moviehub.feature.profile.presentation.profile.ProfileScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_profile
import moviehub.composeapp.generated.resources.ic_profile_selected
import moviehub.composeapp.generated.resources.profile_tab_label
import org.jetbrains.compose.resources.stringResource

object ProfileTab : NavigationTab() {

    override val route = "profile"

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 4L,
            title = stringResource(Res.string.profile_tab_label),
            icon = Res.drawable.ic_profile,
            activeIcon = Res.drawable.ic_profile_selected,
        )

    @Composable
    override fun Content() = ProfileScreen()
}
