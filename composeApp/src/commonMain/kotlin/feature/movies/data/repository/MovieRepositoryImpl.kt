package feature.movies.data.repository

import com.plusmobileapps.konnectivity.Konnectivity
import core.utils.FailureResponseException
import core.utils.FirebaseConstants
import core.utils.FirebaseMovieExistException
import core.utils.FirebaseMovieNotExistException
import core.utils.NoInternetConnectionException
import core.utils.Resource
import core.utils.runWithTimeout
import core.utils.safeApiCall
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import feature.movies.data.api.MovieApi
import feature.movies.data.api.dto.CastResponse
import feature.movies.data.api.dto.MovieDetailsDto
import feature.movies.data.api.dto.toDomain
import feature.movies.domain.model.CastData
import feature.movies.domain.model.FirebaseMovie
import feature.movies.domain.model.FirebaseRating
import feature.movies.domain.model.Movie
import feature.movies.domain.model.MovieDetails
import feature.movies.domain.model.calculateAvgRating
import feature.movies.domain.model.toFirebaseMovie
import io.ktor.client.call.body
import kotlinx.datetime.Clock

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val firestore: FirebaseFirestore,
    private val konnectivity: Konnectivity,
) : MovieRepository {
    override suspend fun getMovieById(movieId: Long): Resource<MovieDetails> =
        safeApiCall(call = { movieApi.getMovieById(movieId) }) { response ->
            Resource.Success(response.body<MovieDetailsDto>().toDomain())
        }

    override suspend fun getCredits(movieId: Long): Resource<CastData> =
        safeApiCall(call = { movieApi.getCredits(movieId) }) { response ->
            Resource.Success(response.body<CastResponse>().toDomain())
        }

    override suspend fun getFirebaseMovieById(movieId: Long): Resource<FirebaseMovie> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .where {
                    FirebaseConstants.MOVIE_ID equalTo movieId
                }
                .get()
            if (querySnapshot.documents.isNotEmpty()) {
                val movie =
                    querySnapshot.documents.map { it.data(FirebaseMovie.serializer()) }.first()
                Resource.Success(movie)
            } else {
                Resource.Failure(FailureResponseException())
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getTopRatedFirebaseMovies(): Resource<List<FirebaseMovie>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .orderBy(FirebaseConstants.AVERAGE_RATING, Direction.DESCENDING)
                .get()
            val movies = querySnapshot.documents.map { it.data(FirebaseMovie.serializer()) }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getLastUpdatedFirebaseMovies(): Resource<List<FirebaseMovie>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .orderBy(FirebaseConstants.UPDATED_AT, Direction.DESCENDING)
                .limit(20)
                .get()
            val movies = querySnapshot.documents.map { it.data(FirebaseMovie.serializer()) }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addFirebaseMovie(movie: Movie): Resource<DocumentReference> =
        runWithTimeout {
            if (!konnectivity.isConnected) {
                return@runWithTimeout Resource.Failure(NoInternetConnectionException())
            }

            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .where {
                    FirebaseConstants.MOVIE_ID equalTo movie.id
                }
                .get()
            if (querySnapshot.documents.isEmpty()) {
                val firebaseMovie = movie.toFirebaseMovie(
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now(),
                )
                val result = firestore
                    .collection(FirebaseConstants.MOVIES_COLLECTION)
                    .add(firebaseMovie)
                Resource.Success(result)
            } else {
                Resource.Failure(FirebaseMovieExistException())
            }
        }

    override suspend fun addFirebaseRating(
        movieId: Long,
        rating: Double,
        comment: String
    ): Resource<FirebaseMovie> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Resource.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.MOVIES_COLLECTION)
            .where { FirebaseConstants.MOVIE_ID equalTo movieId }
            .get()
        if (querySnapshot.documents.isNotEmpty()) {
            val userId = Firebase.auth.currentUser?.uid ?: return@runWithTimeout Resource.Failure(
                FailureResponseException()
            )
            val document = querySnapshot.documents.first()
            val movie = document.data(FirebaseMovie.serializer())
            val ratingList = movie.ratings.toMutableList()
            val movieIndex = ratingList.indexOfFirst { it.userId == userId }

            if (movieIndex == -1) {
                val firebaseRating = FirebaseRating(
                    userId = userId,
                    rating = rating,
                    comment = comment,
                    createdAt = Clock.System.now(),
                )
                ratingList.add(firebaseRating)
            } else {
                val firebaseRating = ratingList[movieIndex]
                val updatedFirebaseRating = firebaseRating.copy(
                    rating = rating,
                    comment = comment,
                    createdAt = Clock.System.now(),
                )
                ratingList[movieIndex] = updatedFirebaseRating
            }

            val updatedMovie = movie.copy(
                ratings = ratingList,
                averageRating = ratingList.calculateAvgRating(),
                updatedAt = Clock.System.now(),
            )
            firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .document(document.id)
                .update(updatedMovie)
            Resource.Success(updatedMovie)
        } else {
            Resource.Failure(FirebaseMovieNotExistException())
        }
    }

    override suspend fun deleteFirebaseRating(
        movieId: Long,
        rating: FirebaseRating,
    ): Resource<FirebaseMovie> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Resource.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.MOVIES_COLLECTION)
            .where { FirebaseConstants.MOVIE_ID equalTo movieId }
            .get()
        if (querySnapshot.documents.isNotEmpty()) {
            val document = querySnapshot.documents.first()
            val movie = document.data(FirebaseMovie.serializer())
            val ratingList = movie.ratings.toMutableList()
            ratingList.removeAll { it.userId == rating.userId }

            val updatedMovie = movie.copy(
                ratings = ratingList,
                averageRating = ratingList.calculateAvgRating(),
                updatedAt = Clock.System.now(),
            )

            firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .document(document.id)
                .update(updatedMovie)
            Resource.Success(updatedMovie)
        } else {
            Resource.Failure(FirebaseMovieNotExistException())
        }
    }
}
