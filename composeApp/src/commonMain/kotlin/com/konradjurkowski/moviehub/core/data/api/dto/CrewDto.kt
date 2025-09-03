package com.konradjurkowski.moviehub.core.data.api.dto

import com.konradjurkowski.moviehub.core.domain.model.media.Crew
import kotlinx.serialization.Serializable

@Serializable
data class CrewDto(
    val id: Long,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val imageUrl: String?,
    val adult: Boolean,
    val gender: Long,
    val department: String,
    val job: String,
)

fun CrewDto.toDomain(): Crew {
    return Crew(
        id = id,
        name = name,
        popularity = popularity,
        imageUrl = imageUrl,
        adult = adult,
        gender = gender,
        department = department,
        job = job,
    )
}
