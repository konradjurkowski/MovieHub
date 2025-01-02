package feature.series.data.paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import feature.series.data.api.SeriesApi
import feature.series.data.api.dto.SeriesResponse
import feature.series.data.api.dto.toSeries
import feature.series.domain.model.Series
import io.ktor.client.call.body
import io.ktor.utils.io.errors.IOException

class PopularSeriesPagingSource(
    private val api: SeriesApi,
) : PagingSource<Int, Series>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Series> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.getPopularSeries(page = currentPage)
            val seriesResponse = response.body<SeriesResponse>()
            LoadResult.Page(
                data = seriesResponse.results.map { it.toSeries() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (seriesResponse.results.isEmpty()) null else seriesResponse.page.toInt() + 1,
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Series>): Int? {
        return state.anchorPosition
    }
}
