package com.konradjurkowski.moviehub.core.navigation

import kotlinx.serialization.Serializable

interface AppDestination

sealed interface GlobalDestination : AppDestination {

    @Serializable
    data object QrCodeScannerRoute : GlobalDestination

    @Serializable
    data object MainRoute : GlobalDestination
}
