package feature.series.presentation.preview

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import feature.series.data.repository.SeriesRepository
import feature.series.data.storage.SeriesRegistry
import feature.series.domain.model.SeriesDetails
import feature.series.domain.model.toSeries
import kotlinx.coroutines.Job
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

    private var listenSeriesRegistryJob : Job? = null

    init {
        getSeriesDetails()
    }

    override fun getDefaultState() = SeriesPreviewState.Idle

    override fun processIntent(intent: SeriesPreviewIntent) {
        when (intent) {
            SeriesPreviewIntent.BackPressed -> sendSideEffect(SeriesPreviewSideEffect.NavigateBack)
            SeriesPreviewIntent.Refresh -> getSeriesDetails()
            is SeriesPreviewIntent.SeriesAddPressed -> addSeries(intent.series)
        }
    }

    private fun getSeriesDetails() {
        updateViewState { SeriesPreviewState.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureSeries = async { seriesRepository.getSeriesById(seriesId) }
            val futureCredits = async { seriesRepository.getCredits(seriesId) }

            val seriesResult = futureSeries.await()
            val creditsResult = futureCredits.await()

            val resultList = listOf(seriesResult, creditsResult)

            if (resultList.all { it.isSuccess() }) {
                val data = SeriesPreviewState.Success(
                    series = seriesResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                )
                updateViewState { data }
                initializeListeners()
                return@launch
            }

            updateViewState { SeriesPreviewState.Error() }
        }
    }

    private fun addSeries(series: SeriesDetails) {
        sendSideEffect(SeriesPreviewSideEffect.ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = seriesRepository.addFirebaseSeries(series.toSeries())) {
                is Response.Success -> {
                    seriesRegistry.addSeries(seriesId)
                    _viewState.transformIf<SeriesPreviewState.Success> { copy(isSeriesAdded = true) }
                    sendSideEffect(SeriesPreviewSideEffect.HideLoaderWithSuccess)
                }
                is Response.Failure -> {
                    sendSideEffect(SeriesPreviewSideEffect.HideLoaderWithError(result.error))
                }
            }
        }
    }

    private fun initializeListeners() {
        if (listenSeriesRegistryJob?.isActive == true) return

        listenSeriesRegistryJob = seriesRegistry.series.onEach { seriesIds ->
            _viewState.transformIf<SeriesPreviewState.Success> { copy(isSeriesAdded = seriesIds.contains(seriesId)) }
        }.launchIn(screenModelScope)
    }
}
