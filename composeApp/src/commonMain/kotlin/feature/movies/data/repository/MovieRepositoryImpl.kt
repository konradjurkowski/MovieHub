package feature.movies.data.repository

import com.plusmobileapps.konnectivity.Konnectivity
import core.model.Response
import core.utils.FailureResponseException
import core.utils.constants.FirebaseConstants
import core.utils.FirebaseMovieExistException
import core.utils.FirebaseMovieNotExistException
import core.utils.NoInternetConnectionException
import core.utils.runWithTimeout
import core.utils.safeApiCall
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import feature.movies.data.api.MovieApi
import core.model.media.dto.CastResponse
import feature.movies.data.api.dto.MovieDetailsDto
import core.model.media.dto.toDomain
import feature.movies.data.api.dto.toDomain
import feature.movies.data.storage.MovieRegistry
import core.model.media.CastData
import core.model.media.Video
import core.model.media.dto.CountryWatchProviders
import core.model.media.dto.VideoResponse
import core.model.media.dto.WatchProviderResponse
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
    private val movieRegistry: MovieRegistry,
    private val konnectivity: Konnectivity,
) : MovieRepository {

    override suspend fun getMovieById(movieId: Long): Response<MovieDetails> {
        val videListResult = getVideos(movieId)
        if (videListResult.isFailure()) return Response.Failure(FailureResponseException())

        val watchProvidersResult = getWatchProviders(movieId)
        if (watchProvidersResult.isFailure()) return Response.Failure(FailureResponseException())

        val videoList = videListResult.getSuccess() ?: emptyList()
        val watchProviders = watchProvidersResult.getSuccess()
        return safeApiCall(call = { movieApi.getMovieById(movieId) }) { response ->
            val movie = response.body<MovieDetailsDto>().toDomain()
            val updatedMovie = movie.copy(
                videoList = videoList,
                countryWatchProviders = watchProviders,
            )
            Response.Success(updatedMovie)
        }
    }

    override suspend fun getVideos(movieId: Long): Response<List<Video>> =
        safeApiCall(call = { movieApi.getVideos(movieId) }) { response ->
            val videos = response.body<VideoResponse>().results.map { it.toDomain() }
            Response.Success(videos)
        }

    override suspend fun getWatchProviders(movieId: Long): Response<CountryWatchProviders> =
        safeApiCall(call = { movieApi.getWatchProviders(movieId) }) { response ->
            Response.Success(response.body<WatchProviderResponse>().results)
        }

    override suspend fun getCredits(movieId: Long): Response<CastData> =
        safeApiCall(call = { movieApi.getCredits(movieId) }) { response ->
            Response.Success(response.body<CastResponse>().toDomain())
        }

    override suspend fun getFirebaseMovieById(movieId: Long): Response<FirebaseMovie> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .where {
                    FirebaseConstants.MOVIE_ID equalTo movieId
                }
                .get()
            if (querySnapshot.documents.isEmpty()) return Response.Failure(FailureResponseException())

            val movie = querySnapshot.documents.map { it.data(FirebaseMovie.serializer()) }.first()
            Response.Success(movie)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getFirebaseMovies(): Response<List<FirebaseMovie>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .orderBy(FirebaseConstants.AVERAGE_RATING, Direction.DESCENDING)
                .get()
            val movies = querySnapshot.documents.map { it.data(FirebaseMovie.serializer()) }
            movieRegistry.updateMovies(movies)
            Response.Success(movies)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getLastUpdatedFirebaseMovies(): Response<List<FirebaseMovie>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .orderBy(FirebaseConstants.UPDATED_AT, Direction.DESCENDING)
                .limit(20)
                .get()
            val movies = querySnapshot.documents.map { it.data(FirebaseMovie.serializer()) }
            Response.Success(movies)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addFirebaseMovie(movie: Movie): Response<DocumentReference> =
        runWithTimeout {
            if (!konnectivity.isConnected) {
                return@runWithTimeout Response.Failure(NoInternetConnectionException())
            }

            val querySnapshot = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .where {
                    FirebaseConstants.MOVIE_ID equalTo movie.id
                }
                .get()
            if (querySnapshot.documents.isNotEmpty()) {
                return@runWithTimeout Response.Failure(FirebaseMovieExistException())
            }

            val firebaseMovie = movie.toFirebaseMovie(
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now(),
            )
            val result = firestore
                .collection(FirebaseConstants.MOVIES_COLLECTION)
                .add(firebaseMovie)
            Response.Success(result)
        }

    override suspend fun addFirebaseRating(
        movieId: Long,
        rating: Double,
        comment: String
    ): Response<FirebaseMovie> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Response.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.MOVIES_COLLECTION)
            .where { FirebaseConstants.MOVIE_ID equalTo movieId }
            .get()
        if (querySnapshot.documents.isEmpty()) {
            return@runWithTimeout Response.Failure(FirebaseMovieNotExistException())
        }

        val userId = Firebase.auth.currentUser?.uid
            ?: return@runWithTimeout Response.Failure(FailureResponseException())

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
        Response.Success(updatedMovie)
    }

    override suspend fun deleteFirebaseRating(
        movieId: Long,
        rating: FirebaseRating,
    ): Response<FirebaseMovie> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Response.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.MOVIES_COLLECTION)
            .where { FirebaseConstants.MOVIE_ID equalTo movieId }
            .get()
        if (querySnapshot.documents.isEmpty()) {
            return@runWithTimeout Response.Failure(FirebaseMovieNotExistException())
        }

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
        Response.Success(updatedMovie)
    }
}
