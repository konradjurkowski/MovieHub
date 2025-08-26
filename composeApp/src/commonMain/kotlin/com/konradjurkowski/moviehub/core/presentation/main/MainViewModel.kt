package com.konradjurkowski.moviehub.core.presentation.main

import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainEvent
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainEvent.SetTab
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainIntent
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainIntent.TabPressed
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainState
import com.konradjurkowski.moviehub.feature.home.presentation.tab.HomeTab
import com.konradjurkowski.moviehub.feature.movies.presentation.tab.MoviesTab
import com.konradjurkowski.moviehub.feature.profile.presentation.tab.ProfileTab
import com.konradjurkowski.moviehub.feature.series.presentation.tab.SeriesTab

class MainViewModel : BaseViewModel<MainIntent, MainState, MainEvent>(
    initialState = MainState(tabList = listOf(HomeTab, MoviesTab, SeriesTab, ProfileTab)),
) {

    override fun processIntent(intent: MainIntent) {
        when (intent) {
            is TabPressed -> sendEvent(SetTab(intent.tab))
        }
    }
}
