package com.konradjurkowski.moviehub.feature.movies.data.paging

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.konradjurkowski.moviehub.core.data.api.dto.SearchResponse
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.MovieDto
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.toDomain
import com.konradjurkowski.moviehub.feature.movies.domain.api.MovieApi
import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie
import io.ktor.client.call.body

class SearchMoviePagingSource(
    private val api: MovieApi,
    private val query: String,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.searchMovies(query = query, page = currentPage)
            val searchResponse = response.body<SearchResponse<MovieDto>>()
            val movieList = searchResponse.results.map { it.toDomain() }
            LoadResult.Page(
                data = movieList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movieList.isEmpty()) null else searchResponse.page.toInt() + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
