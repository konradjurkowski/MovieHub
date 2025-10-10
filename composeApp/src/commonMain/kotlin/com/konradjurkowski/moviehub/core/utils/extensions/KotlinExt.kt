package com.konradjurkowski.moviehub.core.utils.extensions

import kotlin.math.roundToInt

fun Double.round(decimals: Int): Double {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return (roundedValue / dotAt) + (roundedValue % dotAt).toDouble() / dotAt
}
