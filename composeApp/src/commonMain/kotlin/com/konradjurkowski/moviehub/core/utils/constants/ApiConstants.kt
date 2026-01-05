package com.konradjurkowski.moviehub.core.utils.constants

object ApiConstants {
    const val REQUEST_TIMEOUT_IN_MS = 30000L

    object MovieHub {
        const val NAME = "movie_hub"
        const val BASE_URL = "http://192.168.0.43:8080"
    }

    object Cloudinary {
        const val NAME = "cloudinary"
        const val BASE_URL = "https://api.cloudinary.com"
    }

    object Auth {
        const val NAME = "auth_refresh"
        const val BASE_URL = MovieHub.BASE_URL
    }
}
