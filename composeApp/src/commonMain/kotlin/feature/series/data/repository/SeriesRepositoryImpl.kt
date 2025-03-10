package feature.series.data.repository

import com.plusmobileapps.konnectivity.Konnectivity
import core.model.Response
import core.utils.FailureResponseException
import core.utils.constants.FirebaseConstants
import core.utils.FirebaseSeriesExistException
import core.utils.FirebaseSeriesNotExistException
import core.utils.NoInternetConnectionException
import core.utils.runWithTimeout
import core.utils.safeApiCall
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import core.model.media.dto.CastResponse
import core.model.media.dto.toDomain
import core.model.media.CastData
import core.model.media.Video
import core.model.media.dto.CountryWatchProviders
import core.model.media.dto.VideoResponse
import core.model.media.dto.WatchProviderResponse
import feature.movies.domain.model.FirebaseRating
import feature.movies.domain.model.calculateAvgRating
import feature.series.data.api.SeriesApi
import feature.series.data.api.dto.SeriesDetailsDto
import feature.series.data.api.dto.toDomain
import feature.series.data.storage.SeriesRegistry
import feature.series.domain.model.FirebaseSeries
import feature.series.domain.model.Series
import feature.series.domain.model.SeriesDetails
import feature.series.domain.model.toFirebaseSeries
import io.ktor.client.call.body
import kotlinx.datetime.Clock

class SeriesRepositoryImpl(
    private val seriesApi: SeriesApi,
    private val firestore: FirebaseFirestore,
    private val seriesRegistry: SeriesRegistry,
    private val konnectivity: Konnectivity,
) : SeriesRepository {

    override suspend fun getSeriesById(seriesId: Long): Response<SeriesDetails> {
        val videListResult = getVideos(seriesId)
        if (videListResult.isFailure()) return Response.Failure(FailureResponseException())

        val watchProvidersResult = getWatchProviders(seriesId)
        if (watchProvidersResult.isFailure()) return Response.Failure(FailureResponseException())

        val videoList = videListResult.getSuccess() ?: emptyList()
        val watchProviders = watchProvidersResult.getSuccess()
        return safeApiCall(call = { seriesApi.getSeriesById(seriesId) }) { response ->
            val series = response.body<SeriesDetailsDto>().toDomain()
            val updatedSeries = series.copy(
                videoList = videoList,
                countryWatchProviders = watchProviders,
            )
            Response.Success(updatedSeries)
        }
    }

    override suspend fun getVideos(seriesId: Long): Response<List<Video>> =
        safeApiCall(call = { seriesApi.getVideos(seriesId) }) { response ->
            Response.Success(response.body<VideoResponse>().results.map { it.toDomain() })
        }

    override suspend fun getWatchProviders(seriesId: Long): Response<CountryWatchProviders> =
        safeApiCall(call = { seriesApi.getWatchProviders(seriesId) }) { response ->
            Response.Success(response.body<WatchProviderResponse>().results)
        }

    override suspend fun getCredits(seriesId: Long): Response<CastData> =
        safeApiCall(call = { seriesApi.getCredits(seriesId = seriesId) }) { response ->
            Response.Success(response.body<CastResponse>().toDomain())
        }

    override suspend fun getFirebaseSeriesById(seriesId: Long): Response<FirebaseSeries> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .where { FirebaseConstants.SERIES_ID equalTo seriesId }
                .get()
            if (querySnapshot.documents.isEmpty()) {
                return Response.Failure(FailureResponseException())
            }

            val series = querySnapshot.documents.map { it.data(FirebaseSeries.serializer()) }.first()
            Response.Success(series)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getFirebaseSeries(): Response<List<FirebaseSeries>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .orderBy(FirebaseConstants.AVERAGE_RATING, Direction.DESCENDING)
                .get()
            val series = querySnapshot.documents.map { it.data(FirebaseSeries.serializer()) }
            seriesRegistry.updateSeries(series)
            Response.Success(series)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getLastUpdatedFirebaseSeries(): Response<List<FirebaseSeries>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .orderBy(FirebaseConstants.UPDATED_AT, Direction.DESCENDING)
                .limit(20)
                .get()
            val series = querySnapshot.documents.map { it.data(FirebaseSeries.serializer()) }
            Response.Success(series)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addFirebaseSeries(series: Series): Response<DocumentReference> =
        runWithTimeout {
            if (!konnectivity.isConnected) {
                return@runWithTimeout Response.Failure(NoInternetConnectionException())
            }

            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .where { FirebaseConstants.SERIES_ID equalTo series.id }
                .get()
            if (querySnapshot.documents.isNotEmpty()) {
                return@runWithTimeout Response.Failure(FirebaseSeriesExistException())
            }

            val firebaseSeries = series.toFirebaseSeries(
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now(),
            )
            val result = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .add(firebaseSeries)
            Response.Success(result)
        }

    override suspend fun addFirebaseRating(
        seriesId: Long,
        rating: Double,
        comment: String
    ): Response<FirebaseSeries> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Response.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.SERIES_COLLECTION)
            .where { FirebaseConstants.SERIES_ID equalTo seriesId }
            .get()
        if (querySnapshot.documents.isEmpty()) {
            return@runWithTimeout Response.Failure(FirebaseSeriesNotExistException())
        }

        val userId = Firebase.auth.currentUser?.uid
            ?: return@runWithTimeout Response.Failure(FailureResponseException())
        val document = querySnapshot.documents.first()
        val series = document.data(FirebaseSeries.serializer())
        val ratingList = series.ratings.toMutableList()
        val seriesIndex = ratingList.indexOfFirst { it.userId == userId }

        if (seriesIndex == -1) {
            val firebaseRating = FirebaseRating(
                userId = userId,
                rating = rating,
                comment = comment,
                createdAt = Clock.System.now(),
            )
            ratingList.add(firebaseRating)
        } else {
            val firebaseRating = ratingList[seriesIndex]
            val updatedFirebaseRating = firebaseRating.copy(
                rating = rating,
                comment = comment,
                createdAt = Clock.System.now(),
            )
            ratingList[seriesIndex] = updatedFirebaseRating
        }

        val updatedSeries = series.copy(
            ratings = ratingList,
            averageRating = ratingList.calculateAvgRating(),
            updatedAt = Clock.System.now(),
        )
        firestore
            .collection(FirebaseConstants.SERIES_COLLECTION)
            .document(document.id)
            .update(updatedSeries)
        Response.Success(updatedSeries)
    }

    override suspend fun deleteFirebaseRating(
        seriesId: Long,
        rating: FirebaseRating
    ): Response<FirebaseSeries> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Response.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.SERIES_COLLECTION)
            .where { FirebaseConstants.SERIES_ID equalTo seriesId }
            .get()
        if (querySnapshot.documents.isEmpty()) {
            return@runWithTimeout Response.Failure(FirebaseSeriesNotExistException())
        }

        val document = querySnapshot.documents.first()
        val series = document.data(FirebaseSeries.serializer())
        val ratingList = series.ratings.toMutableList()
        ratingList.removeAll { it.userId == rating.userId }

        val updatedSeries = series.copy(
            ratings = ratingList,
            averageRating = ratingList.calculateAvgRating(),
            updatedAt = Clock.System.now(),
        )

        firestore
            .collection(FirebaseConstants.SERIES_COLLECTION)
            .document(document.id)
            .update(updatedSeries)
        Response.Success(updatedSeries)
    }
}
