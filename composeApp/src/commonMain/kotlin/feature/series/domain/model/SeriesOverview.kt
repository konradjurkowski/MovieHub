package feature.series.domain.model

data class SeriesOverview(
    val popularSeries: List<Series>,
    val topRatedSeries: List<Series>
)
