package feature.movies.data.repository

import core.model.Response
import dev.gitlive.firebase.firestore.DocumentReference
import core.model.media.CastData
import core.model.media.Video
import core.model.media.dto.CountryWatchProviders
import feature.movies.domain.model.FirebaseMovie
import feature.movies.domain.model.FirebaseRating
import feature.movies.domain.model.Movie
import feature.movies.domain.model.MovieDetails

interface MovieRepository {
    suspend fun getMovieById(movieId: Long): Response<MovieDetails>
    suspend fun getVideos(movieId: Long): Response<List<Video>>
    suspend fun getWatchProviders(movieId: Long): Response<CountryWatchProviders>
    suspend fun getCredits(movieId: Long): Response<CastData>
    suspend fun getFirebaseMovieById(movieId: Long): Response<FirebaseMovie>
    suspend fun getFirebaseMovies(): Response<List<FirebaseMovie>>
    suspend fun getLastUpdatedFirebaseMovies(): Response<List<FirebaseMovie>>
    suspend fun addFirebaseMovie(movie: Movie): Response<DocumentReference>
    suspend fun addFirebaseRating(movieId: Long, rating: Double, comment: String): Response<FirebaseMovie>
    suspend fun deleteFirebaseRating(movieId: Long, rating: FirebaseRating) : Response<FirebaseMovie>
}
