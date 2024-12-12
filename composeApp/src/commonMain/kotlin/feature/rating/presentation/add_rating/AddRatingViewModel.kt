package feature.rating.presentation.add_rating

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshMovie
import core.tools.event_bus.RefreshMovieList
import core.tools.event_bus.RefreshSeries
import core.tools.event_bus.RefreshSeriesList
import core.tools.validator.FormValidator
import core.utils.Resource
import feature.movies.data.repository.MovieRepository
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.launch

class AddRatingViewModel(
    private val mediaId: Long,
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val formValidator: FormValidator,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<AddRatingIntent, AddRatingSideEffect, AddRatingState>() {

    override fun getDefaultState() = AddRatingState()

    override fun processIntent(intent: AddRatingIntent) {
        when (intent) {
            is AddRatingIntent.CommentUpdated -> updateViewState { copy(comment = intent.comment) }
            is AddRatingIntent.RatingUpdated -> updateViewState { copy(rating = intent.rating) }
            is AddRatingIntent.Submit -> addRating(intent.isMovie, intent.rating, intent.comment)
            is AddRatingIntent.LoadInitialData -> {
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
        } else {
            addSeriesRating(rating, comment)
        }
    }

    private fun addMovieRating(rating: Double, comment: String) {
        if (viewState.value.ratingState.isLoading()) return

        val commentValidation = formValidator.basicValidation(comment)
        updateViewState { copy(commentValidation = commentValidation) }
        if (!commentValidation.successful) return

        screenModelScope.launch(dispatchersProvider.io) {
            updateViewState { copy(ratingState = Resource.Loading) }
            val result = movieRepository.addFirebaseRating(mediaId, rating, comment)
            when (result) {
                is Resource.Success -> {
                    eventBus.invokeEvent(RefreshMovie(mediaId))
                    eventBus.invokeEvent(RefreshMovieList)
                    sendSideEffect(AddRatingSideEffect.Success)
                }

                is Resource.Failure -> {
                    sendSideEffect(AddRatingSideEffect.ShowError(result.error))
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

        val commentValidation = formValidator.basicValidation(comment)
        updateViewState { copy(commentValidation = commentValidation) }
        if (!commentValidation.successful) return

        screenModelScope.launch(dispatchersProvider.io) {
            updateViewState { copy(ratingState = Resource.Loading) }
            val result = seriesRepository.addFirebaseRating(mediaId, rating, comment)
            when (result) {
                is Resource.Success -> {
                    eventBus.invokeEvent(RefreshSeries(mediaId))
                    eventBus.invokeEvent(RefreshSeriesList)
                    sendSideEffect(AddRatingSideEffect.Success)
                }

                is Resource.Failure -> {
                    sendSideEffect(AddRatingSideEffect.ShowError(result.error))
                }

                else -> {
                    // NO - OP
                }
            }
            updateViewState { copy(ratingState = result) }
        }
    }
}
