package feature.series.presentation.details

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshSeries
import feature.auth.data.remote.AuthService
import feature.movies.domain.model.FirebaseRating
import feature.series.data.repository.SeriesRepository
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

    override fun getDefaultState() = SeriesDetailsState.Idle

    override fun processIntent(intent: SeriesDetailsIntent) {
        when (intent) {
            SeriesDetailsIntent.BackPressed -> sendSideEffect(SeriesDetailsSideEffect.NavigateBack)
            SeriesDetailsIntent.Refresh -> getSeriesDetails()
            is SeriesDetailsIntent.SetTab -> _viewState.transformIf<SeriesDetailsState.Success> { copy(selectedTab = intent.tab) }
            is SeriesDetailsIntent.AddCommentPressed -> sendSideEffect(SeriesDetailsSideEffect.GoToAddComment(intent.firebaseRating))
            is SeriesDetailsIntent.DeleteCommentPressed -> deleteComment(intent.firebaseRating)
        }
    }

    private fun getSeriesDetails() {
        if (_viewState.value == SeriesDetailsState.Idle) updateViewState { SeriesDetailsState.Loading }
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
                val data = SeriesDetailsState.Success(
                    series = seriesResult.getSuccess()!!,
                    firebaseSeries = firebaseSeriesResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                    users = usersResult.getSuccess()!!,
                )
                updateViewState { data }
                return@launch
            }

            updateViewState { SeriesDetailsState.Error() }
        }
    }

    private fun deleteComment(rating: FirebaseRating) {
        sendSideEffect(SeriesDetailsSideEffect.ShowLoader)

        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = seriesRepository.deleteFirebaseRating(seriesId, rating)) {
                is Response.Success -> {
                    sendSideEffect(SeriesDetailsSideEffect.HideLoaderWithSuccess)
                    getSeriesDetails()
                }
                is Response.Failure -> {
                    sendSideEffect(SeriesDetailsSideEffect.HideLoaderWithError(result.error))
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
