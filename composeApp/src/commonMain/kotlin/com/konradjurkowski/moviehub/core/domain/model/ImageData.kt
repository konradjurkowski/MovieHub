package com.konradjurkowski.moviehub.core.domain.model

class ImageData(val array: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ImageData) return false
        return array.contentEquals(other.array)
    }

    override fun hashCode(): Int {
        return array.contentHashCode()
    }
}

fun ByteArray.toImageData() = ImageData(this)
