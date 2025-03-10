package feature.series.presentation.preview

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.model.Response
import core.model.media.getVideoUrl
import core.tools.dispatcher.DispatchersProvider
import feature.series.data.repository.SeriesRepository
import feature.series.data.storage.SeriesRegistry
import feature.series.domain.model.SeriesDetails
import feature.series.domain.model.toSeries
import feature.series.presentation.preview.SeriesPreviewIntent.BackPressed
import feature.series.presentation.preview.SeriesPreviewIntent.SeriesAddPressed
import feature.series.presentation.preview.SeriesPreviewIntent.Refresh
import feature.series.presentation.preview.SeriesPreviewIntent.VideoPressed
import feature.series.presentation.preview.SeriesPreviewSideEffect.HideLoaderWithError
import feature.series.presentation.preview.SeriesPreviewSideEffect.HideLoaderWithSuccess
import feature.series.presentation.preview.SeriesPreviewSideEffect.NavigateBack
import feature.series.presentation.preview.SeriesPreviewSideEffect.OpenUrl
import feature.series.presentation.preview.SeriesPreviewSideEffect.ShowLoader
import feature.series.presentation.preview.SeriesPreviewState.Idle
import feature.series.presentation.preview.SeriesPreviewState.Loading
import feature.series.presentation.preview.SeriesPreviewState.Success
import feature.series.presentation.preview.SeriesPreviewState.Error
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

    override fun getDefaultState() = Idle

    override fun processIntent(intent: SeriesPreviewIntent) {
        when (intent) {
            BackPressed -> sendSideEffect(NavigateBack)
            Refresh -> getSeriesDetails()
            is SeriesAddPressed -> addSeries(intent.series)
            is VideoPressed -> sendSideEffect(OpenUrl(intent.video.getVideoUrl()))
        }
    }

    private fun getSeriesDetails() {
        updateViewState { Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureSeries = async { seriesRepository.getSeriesById(seriesId) }
            val futureCredits = async { seriesRepository.getCredits(seriesId) }

            val seriesResult = futureSeries.await()
            val creditsResult = futureCredits.await()

            val resultList = listOf(seriesResult, creditsResult)

            if (resultList.all { it.isSuccess() }) {
                val data = Success(
                    series = seriesResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                )
                updateViewState { data }
                initializeListeners()
                return@launch
            }

            updateViewState { Error() }
        }
    }

    private fun addSeries(series: SeriesDetails) {
        sendSideEffect(ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = seriesRepository.addFirebaseSeries(series.toSeries())) {
                is Response.Success -> {
                    seriesRegistry.addSeries(seriesId)
                    _viewState.transformIf<Success> { copy(isSeriesAdded = true) }
                    sendSideEffect(HideLoaderWithSuccess)
                }
                is Response.Failure -> {
                    sendSideEffect(HideLoaderWithError(result.error))
                }
            }
        }
    }

    private fun initializeListeners() {
        if (listenSeriesRegistryJob?.isActive == true) return

        listenSeriesRegistryJob = seriesRegistry.series.onEach { seriesIds ->
            _viewState.transformIf<Success> { copy(isSeriesAdded = seriesIds.contains(seriesId)) }
        }.launchIn(screenModelScope)
    }
}
