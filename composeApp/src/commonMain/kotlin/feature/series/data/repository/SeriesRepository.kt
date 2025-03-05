package feature.series.data.repository

import core.model.Response
import dev.gitlive.firebase.firestore.DocumentReference
import core.model.media.CastData
import feature.movies.domain.model.FirebaseRating
import feature.series.domain.model.FirebaseSeries
import feature.series.domain.model.Series
import feature.series.domain.model.SeriesDetails

interface SeriesRepository {
    suspend fun getSeriesById(seriesId: Long): Response<SeriesDetails>
    suspend fun getCredits(seriesId: Long): Response<CastData>
    suspend fun getFirebaseSeriesById(seriesId: Long): Response<FirebaseSeries>
    suspend fun getFirebaseSeries(): Response<List<FirebaseSeries>>
    suspend fun getLastUpdatedFirebaseSeries(): Response<List<FirebaseSeries>>
    suspend fun addFirebaseSeries(series: Series): Response<DocumentReference>
    suspend fun addFirebaseRating(seriesId: Long, rating: Double, comment: String): Response<FirebaseSeries>
    suspend fun deleteFirebaseRating(seriesId: Long, rating: FirebaseRating) : Response<FirebaseSeries>
}
