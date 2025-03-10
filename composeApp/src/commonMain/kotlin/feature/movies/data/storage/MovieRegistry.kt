package feature.movies.data.storage

import feature.movies.domain.model.FirebaseMovie
import kotlinx.coroutines.flow.StateFlow

interface MovieRegistry {
    val movies: StateFlow<List<Long>>
    fun updateMovies(movies: List<FirebaseMovie>)
    fun addMovie(movieId: Long)
}
