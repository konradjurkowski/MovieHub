package feature.rating.presentation.add_rating

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshMovie
import core.tools.event_bus.RefreshSeries
import core.utils.Resource
import feature.movies.data.repository.MovieRepository
import feature.series.data.repository.SeriesRepository
import feature.rating.presentation.add_rating.AddRatingIntent.CommentUpdated
import feature.rating.presentation.add_rating.AddRatingIntent.RatingUpdated
import feature.rating.presentation.add_rating.AddRatingIntent.Submit
import feature.rating.presentation.add_rating.AddRatingIntent.LoadInitialData
import feature.rating.presentation.add_rating.AddRatingSideEffect.ShowError
import feature.rating.presentation.add_rating.AddRatingSideEffect.ShowSuccessAndNavigateBack
import kotlinx.coroutines.launch

class AddRatingViewModel(
    private val mediaId: Long,
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<AddRatingIntent, AddRatingSideEffect, AddRatingState>() {

    override fun getDefaultState() = AddRatingState()

    override fun processIntent(intent: AddRatingIntent) {
        when (intent) {
            is CommentUpdated -> updateViewState { copy(comment = intent.comment) }
            is RatingUpdated -> updateViewState { copy(rating = intent.rating) }
            is Submit -> addRating(intent.isMovie, intent.rating, intent.comment)

            is LoadInitialData -> {
                if (intent.firebaseRating == null) return

                updateViewState {
                    copy(
                        rating = intent.firebaseRating.rating,
                        comment = intent.firebaseRating.comment,
                    )
                }
            }
        }
    }

    private fun addRating(
        isMovie: Boolean,
        rating: Double,
        comment: String,
    ) {
        if (isMovie) {
            addMovieRating(rating, comment)
            return
        }

        addSeriesRating(rating, comment)
    }

    private fun addMovieRating(rating: Double, comment: String) {
        if (viewState.value.ratingState.isLoading()) return

        screenModelScope.launch(dispatchersProvider.io) {
            updateViewState { copy(ratingState = Resource.Loading) }
            val result = movieRepository.addFirebaseRating(mediaId, rating, comment)
            when (result) {
                is Resource.Success -> {
                    eventBus.invokeEvent(RefreshMovie(mediaId))
                    sendSideEffect(ShowSuccessAndNavigateBack)
                }

                is Resource.Failure -> {
                    sendSideEffect(ShowError(result.error))
                }

                else -> {
                    // NO - OP
                }
            }
            updateViewState { copy(ratingState = result) }
        }
    }

    private fun addSeriesRating(rating: Double, comment: String) {
        if (viewState.value.ratingState.isLoading()) return

        screenModelScope.launch(dispatchersProvider.io) {
            updateViewState { copy(ratingState = Resource.Loading) }
            val result = seriesRepository.addFirebaseRating(mediaId, rating, comment)
            when (result) {
                is Resource.Success -> {
                    eventBus.invokeEvent(RefreshSeries(mediaId))
                    sendSideEffect(ShowSuccessAndNavigateBack)
                }

                is Resource.Failure -> {
                    sendSideEffect(ShowError(result.error))
                }

                else -> {
                    // NO - OP
                }
            }
            updateViewState { copy(ratingState = result) }
        }
    }
}
