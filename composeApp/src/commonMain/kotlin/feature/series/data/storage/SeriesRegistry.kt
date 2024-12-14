package feature.series.data.storage

import feature.series.domain.model.FirebaseSeries
import kotlinx.coroutines.flow.StateFlow

interface SeriesRegistry {
    val series: StateFlow<List<Long>>
    fun updateSeries(series: List<FirebaseSeries>)
    fun addSeries(seriesId: Long)
}
