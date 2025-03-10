package feature.movies.data.storage

import feature.movies.domain.model.FirebaseMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieRegistryImpl : MovieRegistry {

    private val _movies = MutableStateFlow<List<Long>>(emptyList())

    override val movies = _movies.asStateFlow()

    override fun updateMovies(movies: List<FirebaseMovie>) {
        val movieIds = movies.map { it.movieId }
        _movies.value = movieIds.toSet().toList()
    }

    override fun addMovie(movieId: Long) {
        val movieIds = _movies.value.toMutableList()
        movieIds.add(movieId)
        _movies.value = movieIds.toSet().toList()
    }
}
