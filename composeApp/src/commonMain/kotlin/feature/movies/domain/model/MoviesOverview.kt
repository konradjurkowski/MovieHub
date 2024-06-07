package feature.movies.domain.model

data class MoviesOverview(
    val popularMovies: List<Movie>,
    val topRatedMovies: List<Movie>,
)
