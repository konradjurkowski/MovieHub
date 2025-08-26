package com.konradjurkowski.moviehub.core.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> { error("No Navigator provided") }
