package feature.series.data.repository

import core.utils.Resource
import dev.gitlive.firebase.firestore.DocumentReference
import feature.movies.domain.model.CastData
import feature.movies.domain.model.FirebaseRating
import feature.series.domain.model.FirebaseSeries
import feature.series.domain.model.Series
import feature.series.domain.model.SeriesDetails

interface SeriesRepository {
    suspend fun getSeriesById(seriesId: Long): Resource<SeriesDetails>
    suspend fun getCredits(seriesId: Long): Resource<CastData>
    suspend fun getFirebaseSeriesById(seriesId: Long): Resource<FirebaseSeries>
    suspend fun getTopRatedFirebaseSeries(): Resource<List<FirebaseSeries>>
    suspend fun getLastUpdatedFirebaseSeries(): Resource<List<FirebaseSeries>>
    suspend fun addFirebaseSeries(series: Series): Resource<DocumentReference>
    suspend fun addFirebaseRating(seriesId: Long, rating: Double, comment: String): Resource<FirebaseSeries>
    suspend fun deleteFirebaseRating(seriesId: Long, rating: FirebaseRating) : Resource<FirebaseSeries>
}
