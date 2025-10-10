package com.konradjurkowski.moviehub.core.navigation

import kotlinx.serialization.Serializable

interface AppDestination

sealed interface CoreDestination : AppDestination {

    @Serializable
    data object QrCodeScannerRoute : CoreDestination

    @Serializable
    data object MainRoute : CoreDestination
}
