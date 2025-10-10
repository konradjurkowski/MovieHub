package com.konradjurkowski.moviehub.core.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher

interface DispatchersProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: MainCoroutineDispatcher
}

internal class CoroutineDispatchersProviderImpl(
    override val default: CoroutineDispatcher = Dispatchers.Default,
    override val io: CoroutineDispatcher = Dispatchers.IO,
    override val main: MainCoroutineDispatcher = Dispatchers.Main,
) : DispatchersProvider
