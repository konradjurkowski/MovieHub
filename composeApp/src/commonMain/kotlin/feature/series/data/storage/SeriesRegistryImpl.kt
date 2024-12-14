package feature.series.data.storage

import feature.series.domain.model.FirebaseSeries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SeriesRegistryImpl : SeriesRegistry {

    private val _series = MutableStateFlow<List<Long>>(emptyList())

    override val series = _series.asStateFlow()

    override fun updateSeries(series: List<FirebaseSeries>) {
        val seriesIds = series.map { it.seriesId }
        _series.value = seriesIds.toSet().toList()
    }

    override fun addSeries(seriesId: Long) {
        val seriesIds = _series.value.toMutableList()
        seriesIds.add(seriesId)
        _series.value = seriesIds.toSet().toList()
    }
}
