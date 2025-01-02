package feature.movies.data.paging

import androidx.paging.PagingSource
import app.cash.paging.PagingState
import feature.movies.data.api.MovieApi
import feature.movies.data.api.dto.MoviesResponse
import feature.movies.data.api.dto.toDomain
import feature.movies.domain.model.Movie
import io.ktor.client.call.body
import io.ktor.utils.io.errors.IOException

class MoviePagingSource(
    private val api: MovieApi,
    private val query: String,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.searchMovies(query = query, page = currentPage)
            val moviesResponse = response.body<MoviesResponse>()
            LoadResult.Page(
                data = moviesResponse.results.map { it.toDomain() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (moviesResponse.results.isEmpty()) null else moviesResponse.page.toInt() + 1,
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
