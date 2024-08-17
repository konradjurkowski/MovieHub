package feature.movies.domain.model

data class Cast(
    val id: Long,
    val name: String,
    val character: String? = null,
    val job: String? = null,
    val profilePath: String? = null,
)

data class CastData(
    val id: Long,
    val cast: List<Cast>,
    val crew: List<Cast>,
)

fun CastData.getDirector(): Cast? {
    return crew.firstOrNull { it.job == "Director" }
}

fun CastData.getWriter(): Cast? {
    return crew.firstOrNull { it.job == "Writer" }
}
