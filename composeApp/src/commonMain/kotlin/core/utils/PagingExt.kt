package core.utils

import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems

sealed class PagingState {
    data object Empty : PagingState()
    data object Loading : PagingState()
    data object Error : PagingState()
    data object Loaded : PagingState()
}

fun <T : Any> LazyPagingItems<T>.isLoadingAppend(): Boolean = this.loadState.append is LoadState.Loading
fun <T : Any> LazyPagingItems<T>.isErrorAppend(): Boolean = this.loadState.append is LoadState.Error


fun <T : Any> LazyPagingItems<T>.getPagingState(): PagingState {
    return when {
        !loadState.refresh.isLoading() && !loadState.refresh.isError() && itemCount == 0 -> PagingState.Empty
        loadState.refresh is LoadState.Loading -> PagingState.Loading
        loadState.refresh is LoadState.Error -> PagingState.Error
        else -> PagingState.Loaded
    }
}

