package com.konradjurkowski.moviehub.core.utils.extensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems

sealed class PagingState {
    data object Empty : PagingState()
    data object Loading : PagingState()
    data object Error : PagingState()
    data object Loaded : PagingState()
}

private fun CombinedLoadStates.refreshState() = mediator?.refresh ?: source.refresh
private fun CombinedLoadStates.appendState() = mediator?.append ?: source.append
fun <T : Any> LazyPagingItems<T>.isLoadingAppend() = loadState.append is LoadState.Loading
fun <T : Any> LazyPagingItems<T>.isErrorAppend() = loadState.append is LoadState.Error

fun <T : Any> LazyPagingItems<T>.getPagingState(): PagingState {
    val refresh = loadState.refreshState()
    val append = loadState.appendState()
    return when {
        refresh is LoadState.Loading -> PagingState.Loading
        refresh is LoadState.Error -> PagingState.Error
        itemCount == 0 && append is LoadState.NotLoading && append.endOfPaginationReached -> PagingState.Empty
        itemCount == 0 -> PagingState.Loading
        else -> PagingState.Loaded
    }
}
