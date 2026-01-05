package com.konradjurkowski.moviehub.feature.auth.navigation

import com.konradjurkowski.moviehub.core.navigation.AppDestination
import kotlinx.serialization.Serializable

sealed interface AuthDestination : AppDestination {

    @Serializable
    data object SplashRoute : AuthDestination

    @Serializable
    data object WelcomeRoute : AuthDestination

    @Serializable
    data object LoginRoute : AuthDestination

    @Serializable
    data object ForgotPasswordRoute : AuthDestination

    @Serializable
    data object RegisterRoute : AuthDestination

    @Serializable
    data class ActivateAccountRoute(val email: String) : AuthDestination

    @Serializable
    data object NotificationPermissionRoute : AuthDestination
}
