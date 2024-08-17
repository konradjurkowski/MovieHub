package feature.movies.data.repository

import core.utils.Resource
import dev.gitlive.firebase.firestore.DocumentReference
import feature.movies.domain.model.CastData
import feature.movies.domain.model.FirebaseMovie
import feature.movies.domain.model.FirebaseRating
import feature.movies.domain.model.Movie
import feature.movies.domain.model.MovieDetails

interface MovieRepository {
    suspend fun getMovieById(movieId: Long): Resource<MovieDetails>
    suspend fun getCredits(movieId: Long): Resource<CastData>
    suspend fun getFirebaseMovieById(movieId: Long): Resource<FirebaseMovie>
    suspend fun getTopRatedFirebaseMovies(): Resource<List<FirebaseMovie>>
    suspend fun getLastUpdatedFirebaseMovies(): Resource<List<FirebaseMovie>>
    suspend fun addFirebaseMovie(movie: Movie): Resource<DocumentReference>
    suspend fun addFirebaseRating(movieId: Long, rating: Double, comment: String): Resource<FirebaseMovie>
    suspend fun deleteFirebaseRating(movieId: Long, rating: FirebaseRating) : Resource<FirebaseMovie>
}
