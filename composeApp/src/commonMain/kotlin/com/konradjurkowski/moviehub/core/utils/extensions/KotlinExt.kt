package com.konradjurkowski.moviehub.core.utils.extensions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt

fun Double.round(decimals: Int): Double {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return (roundedValue / dotAt) + (roundedValue % dotAt).toDouble() / dotAt
}

inline fun <reified T> MutableStateFlow<in T>.transformIf(noinline transform: T.() -> T) {
    if (value !is T) return
    value = transform(value as T)
}

inline fun <reified T> MutableStateFlow<in T>.invokeIf(noinline action: T.() -> Unit) {
    if (value !is T) return
    action(value as T)
}
