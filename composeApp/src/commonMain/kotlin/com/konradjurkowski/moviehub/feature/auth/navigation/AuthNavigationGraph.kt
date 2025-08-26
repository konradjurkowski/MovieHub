package com.konradjurkowski.moviehub.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.konradjurkowski.moviehub.core.utils.extensions.staticComposable
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.ForgotPasswordRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.LoginRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.NotificationPermissionRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.RegisterRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.SplashRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.WelcomeRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.forgot_password.ForgotPasswordScreen
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginScreen
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.NotificationPermissionScreen
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterScreen
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.SplashScreen
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.WelcomeScreen

fun NavGraphBuilder.addAuthGraph() {
    composable<SplashRoute> { SplashScreen() }
    staticComposable<WelcomeRoute> { WelcomeScreen() }
    composable<LoginRoute> { LoginScreen() }
    composable<ForgotPasswordRoute> { ForgotPasswordScreen() }
    composable<RegisterRoute> { RegisterScreen() }
    composable<NotificationPermissionRoute> { NotificationPermissionScreen() }
}
