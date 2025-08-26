package com.konradjurkowski.moviehub.core.presentation.main.ise

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.NavigationTab

@MviIntent
sealed class MainIntent {
    data class TabPressed(val tab: NavigationTab) : MainIntent()
}

@MviEvent
sealed class MainEvent {
    data class SetTab(val tab: NavigationTab) : MainEvent()
}

@MviState
data class MainState(val tabList: List<NavigationTab>)
