package feature.series.data.repository

import com.plusmobileapps.konnectivity.Konnectivity
import core.utils.FailureResponseException
import core.utils.FirebaseConstants
import core.utils.FirebaseSeriesExistException
import core.utils.FirebaseSeriesNotExistException
import core.utils.NoInternetConnectionException
import core.utils.Resource
import core.utils.runWithTimeout
import core.utils.safeApiCall
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import feature.movies.data.api.dto.CastResponse
import feature.movies.data.api.dto.toDomain
import feature.movies.domain.model.CastData
import feature.movies.domain.model.FirebaseRating
import feature.movies.domain.model.calculateAvgRating
import feature.series.data.api.SeriesApi
import feature.series.data.api.dto.SeriesDetailsDto
import feature.series.data.api.dto.toDomain
import feature.series.domain.model.FirebaseSeries
import feature.series.domain.model.Series
import feature.series.domain.model.SeriesDetails
import feature.series.domain.model.toFirebaseSeries
import io.ktor.client.call.body
import kotlinx.datetime.Clock

class SeriesRepositoryImpl(
    private val seriesApi: SeriesApi,
    private val firestore: FirebaseFirestore,
    private val konnectivity: Konnectivity,
) : SeriesRepository {
    override suspend fun getSeriesById(seriesId: Long): Resource<SeriesDetails> =
        safeApiCall(call = { seriesApi.getSeriesById(seriesId) }) { response ->
            Resource.Success(response.body<SeriesDetailsDto>().toDomain())
        }

    override suspend fun getCredits(seriesId: Long): Resource<CastData> =
        safeApiCall(call = { seriesApi.getCredits(seriesId = seriesId) }) { response ->
            Resource.Success(response.body<CastResponse>().toDomain())
        }

    override suspend fun getFirebaseSeriesById(seriesId: Long): Resource<FirebaseSeries> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .where { FirebaseConstants.SERIES_ID equalTo seriesId }
                .get()
            if (querySnapshot.documents.isNotEmpty()) {
                val series =
                    querySnapshot.documents.map { it.data(FirebaseSeries.serializer()) }.first()
                return Resource.Success(series)
            } else {
                Resource.Failure(FailureResponseException())
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getTopRatedFirebaseSeries(): Resource<List<FirebaseSeries>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .orderBy(FirebaseConstants.AVERAGE_RATING, Direction.DESCENDING)
                .get()
            val series = querySnapshot.documents.map { it.data(FirebaseSeries.serializer()) }
            Resource.Success(series)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getLastUpdatedFirebaseSeries(): Resource<List<FirebaseSeries>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .orderBy(FirebaseConstants.UPDATED_AT, Direction.DESCENDING)
                .limit(20)
                .get()
            val series = querySnapshot.documents.map { it.data(FirebaseSeries.serializer()) }
            Resource.Success(series)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addFirebaseSeries(series: Series): Resource<DocumentReference> =
        runWithTimeout {
            if (!konnectivity.isConnected) {
                return@runWithTimeout Resource.Failure(NoInternetConnectionException())
            }

            val querySnapshot = firestore
                .collection(FirebaseConstants.SERIES_COLLECTION)
                .where { FirebaseConstants.SERIES_ID equalTo series.id }
                .get()
            if (querySnapshot.documents.isEmpty()) {
                val firebaseSeries = series.toFirebaseSeries(
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now(),
                )
                val result = firestore
                    .collection(FirebaseConstants.SERIES_COLLECTION)
                    .add(firebaseSeries)
                Resource.Success(result)
            } else {
                Resource.Failure(FirebaseSeriesExistException())
            }
        }

    override suspend fun addFirebaseRating(
        seriesId: Long,
        rating: Double,
        comment: String
    ): Resource<FirebaseSeries> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Resource.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.SERIES_COLLECTION)
            .where { FirebaseConstants.SERIES_ID equalTo seriesId }
            .get()
        if (querySnapshot.documents.isNotEmpty()) {
            val userId = Firebase.auth.currentUser?.uid ?: return@runWithTimeout Resource.Failure(
                FailureResponseException()
            )
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
            Resource.Success(updatedSeries)
        } else {
            Resource.Failure(FirebaseSeriesNotExistException())
        }
    }

    override suspend fun deleteFirebaseRating(
        seriesId: Long,
        rating: FirebaseRating
    ): Resource<FirebaseSeries> = runWithTimeout {
        if (!konnectivity.isConnected) {
            return@runWithTimeout Resource.Failure(NoInternetConnectionException())
        }

        val querySnapshot = firestore
            .collection(FirebaseConstants.SERIES_COLLECTION)
            .where { FirebaseConstants.SERIES_ID equalTo seriesId }
            .get()
        if (querySnapshot.documents.isNotEmpty()) {
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
            Resource.Success(updatedSeries)
        } else {
            Resource.Failure(FirebaseSeriesNotExistException())
        }
    }
}
