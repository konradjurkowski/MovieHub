package feature.series.presentation.details

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.model.Response
import core.model.media.getVideoUrl
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshSeries
import feature.auth.data.remote.AuthService
import feature.movies.domain.model.FirebaseRating
import feature.series.data.repository.SeriesRepository
import feature.series.presentation.details.SeriesDetailsIntent.AddCommentPressed
import feature.series.presentation.details.SeriesDetailsIntent.BackPressed
import feature.series.presentation.details.SeriesDetailsIntent.DeleteCommentPressed
import feature.series.presentation.details.SeriesDetailsIntent.SetTab
import feature.series.presentation.details.SeriesDetailsIntent.Refresh
import feature.series.presentation.details.SeriesDetailsIntent.VideoPressed
import feature.series.presentation.details.SeriesDetailsSideEffect.GoToAddComment
import feature.series.presentation.details.SeriesDetailsSideEffect.HideLoaderWithError
import feature.series.presentation.details.SeriesDetailsSideEffect.HideLoaderWithSuccess
import feature.series.presentation.details.SeriesDetailsSideEffect.NavigateBack
import feature.series.presentation.details.SeriesDetailsSideEffect.OpenUrl
import feature.series.presentation.details.SeriesDetailsSideEffect.ShowLoader
import feature.series.presentation.details.SeriesDetailsState.Idle
import feature.series.presentation.details.SeriesDetailsState.Loading
import feature.series.presentation.details.SeriesDetailsState.Success
import feature.series.presentation.details.SeriesDetailsState.Error
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class SeriesDetailsViewModel(
    private val seriesId: Long,
    private val seriesRepository: SeriesRepository,
    private val authService: AuthService,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SeriesDetailsIntent, SeriesDetailsSideEffect, SeriesDetailsState>() {

    init {
        initListener()
        getSeriesDetails()
    }

    override fun getDefaultState() = Idle

    override fun processIntent(intent: SeriesDetailsIntent) {
        when (intent) {
            BackPressed -> sendSideEffect(NavigateBack)
            Refresh -> getSeriesDetails()
            is SetTab -> _viewState.transformIf<Success> { copy(selectedTab = intent.tab) }
            is AddCommentPressed -> sendSideEffect(GoToAddComment(intent.firebaseRating))
            is DeleteCommentPressed -> deleteComment(intent.firebaseRating)
            is VideoPressed -> sendSideEffect(OpenUrl(intent.video.getVideoUrl()))
        }
    }

    private fun getSeriesDetails() {
        if (_viewState.value == Idle) updateViewState { Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureSeries = async { seriesRepository.getSeriesById(seriesId) }
            val futureFirebaseSeries = async { seriesRepository.getFirebaseSeriesById(seriesId) }
            val futureCredits = async { seriesRepository.getCredits(seriesId) }
            val futureUsers = async { authService.getAllAppUsers() }

            val seriesResult = futureSeries.await()
            val firebaseSeriesResult = futureFirebaseSeries.await()
            val creditsResult = futureCredits.await()
            val usersResult = futureUsers.await()

            val resultList = listOf(seriesResult, firebaseSeriesResult, creditsResult, usersResult)

            if (resultList.all { it.isSuccess() }) {
                val data = Success(
                    series = seriesResult.getSuccess()!!,
                    firebaseSeries = firebaseSeriesResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                    users = usersResult.getSuccess()!!,
                )
                updateViewState { data }
                return@launch
            }

            updateViewState { Error() }
        }
    }

    private fun deleteComment(rating: FirebaseRating) {
        sendSideEffect(ShowLoader)

        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = seriesRepository.deleteFirebaseRating(seriesId, rating)) {
                is Response.Success -> {
                    sendSideEffect(HideLoaderWithSuccess)
                    getSeriesDetails()
                }
                is Response.Failure -> {
                    sendSideEffect(HideLoaderWithError(result.error))
                }
            }
        }
    }

    private fun initListener() {
        screenModelScope.launch {
            eventBus.events
                .filterIsInstance<RefreshSeries>()
                .filter { seriesId == it.seriesId }
                .collectLatest {
                    getSeriesDetails()
                }
        }
    }
}
