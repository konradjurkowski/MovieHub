package feature.series.presentation.preview

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.utils.Resource
import feature.series.data.repository.SeriesRepository
import feature.series.data.storage.SeriesRegistry
import feature.series.domain.model.SeriesDetails
import feature.series.domain.model.toSeries
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SeriesPreviewViewModel(
    private val seriesId: Long,
    private val seriesRepository: SeriesRepository,
    private val seriesRegistry: SeriesRegistry,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SeriesPreviewIntent, SeriesPreviewSideEffect, SeriesPreviewState>() {

    init {
        initializeListeners()
        getSeriesDetails()
    }

    override fun getDefaultState() = SeriesPreviewState()

    override fun processIntent(intent: SeriesPreviewIntent) {
        when (intent) {
            SeriesPreviewIntent.BackPressed -> sendSideEffect(SeriesPreviewSideEffect.NavigateBack)
            SeriesPreviewIntent.Refresh -> getSeriesDetails()
            is SeriesPreviewIntent.SeriesAddPressed -> addSeries(intent.series)
        }
    }

    private fun getSeriesDetails() {
        updateViewState { copy(isLoading = true) }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureSeries = async { seriesRepository.getSeriesById(seriesId) }
            val futureCredits = async { seriesRepository.getCredits(seriesId) }

            val seriesResult = futureSeries.await()
            val creditsResult = futureCredits.await()

            val resultList = listOf(seriesResult, creditsResult)

            if (resultList.all { it.isSuccess() }) {
                updateViewState {
                    copy(
                        series = seriesResult.getSuccess()!!,
                        castData = creditsResult.getSuccess()!!,
                        isLoading = false,
                    )
                }

                return@launch
            }

            updateViewState { copy(isLoading = false) }
        }
    }

    private fun addSeries(series: SeriesDetails) {
        sendSideEffect(SeriesPreviewSideEffect.ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = seriesRepository.addFirebaseSeries(series.toSeries())) {
                is Resource.Success -> {
                    seriesRegistry.addSeries(seriesId)
                    updateViewState { copy(isSeriesAdded = true) }
                    sendSideEffect(SeriesPreviewSideEffect.HideLoaderWithSuccess)
                }
                is Resource.Failure -> {
                    sendSideEffect(SeriesPreviewSideEffect.HideLoaderWithError(result.error))
                }
                else -> {
                    // NO - OP
                }
            }
        }
    }

    private fun initializeListeners() {
        seriesRegistry.series.onEach { seriesIds ->
            updateViewState { copy(isSeriesAdded = seriesIds.contains(seriesId)) }
        }.launchIn(screenModelScope)
    }
}
