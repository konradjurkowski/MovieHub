package feature.series.presentation.series_details

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshSeries
import core.tools.event_bus.RefreshSeriesList
import core.utils.Resource
import feature.auth.data.remote.UserRepository
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
    private val userRepository: UserRepository,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SeriesDetailsIntent, SeriesDetailsSideEffect, SeriesDetailsState>() {

    init {
        initListener()
        getSeriesDetails()
    }

    override fun getDefaultState() = SeriesDetailsState()

    override fun processIntent(intent: SeriesDetailsIntent) {
        when (intent) {
            SeriesDetailsIntent.BackPressed -> sendSideEffect(SeriesDetailsSideEffect.NavigateBack)
            SeriesDetailsIntent.Refresh -> getSeriesDetails()
            is SeriesDetailsIntent.SetTab -> updateViewState { copy(selectedTab = intent.tab) }
            is SeriesDetailsIntent.AddCommentPressed -> sendSideEffect(SeriesDetailsSideEffect.GoToAddComment(intent.firebaseRating))
            is SeriesDetailsIntent.DeleteCommentPressed -> deleteComment(intent.firebaseRating)
        }
    }

    private fun getSeriesDetails() {
        if (!isDataLoaded()) updateViewState { copy(isLoading = true) }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureSeries = async { seriesRepository.getSeriesById(seriesId) }
            val futureFirebaseSeries = async { seriesRepository.getFirebaseSeriesById(seriesId) }
            val futureCredits = async { seriesRepository.getCredits(seriesId) }
            val futureUsers = async { userRepository.getAllUsers() }

            val seriesResult = futureSeries.await()
            val firebaseSeriesResult = futureFirebaseSeries.await()
            val creditsResult = futureCredits.await()
            val usersResult = futureUsers.await()

            val resultList = listOf(seriesResult, firebaseSeriesResult, creditsResult, usersResult)

            if (resultList.all { it.isSuccess() }) {
                updateViewState {
                    copy(
                        series = seriesResult.getSuccess()!!,
                        firebaseSeries = firebaseSeriesResult.getSuccess()!!,
                        castData = creditsResult.getSuccess()!!,
                        users = usersResult.getSuccess()!!,
                        isLoading = false,
                    )
                }
            } else {
                updateViewState { copy(isLoading = false) }
            }
        }
    }

    private fun deleteComment(rating: FirebaseRating) {
        sendSideEffect(SeriesDetailsSideEffect.ShowLoader)

        screenModelScope.launch(dispatchersProvider.io) {
            val result = seriesRepository.deleteFirebaseRating(seriesId, rating)
            when (result) {
                is Resource.Success -> {
                    sendSideEffect(SeriesDetailsSideEffect.HideLoaderWithSuccess)
                    eventBus.invokeEvent(RefreshSeriesList)
                    getSeriesDetails()
                }
                is Resource.Failure -> {
                    sendSideEffect(SeriesDetailsSideEffect.HideLoaderWithError(result.error))
                }
                else -> {
                    // NO - OP
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


    private fun isDataLoaded(): Boolean {
        val state = viewState.value
        return state.series != null &&
                state.firebaseSeries != null &&
                state.castData != null &&
                state.users.isNotEmpty()
    }
}
