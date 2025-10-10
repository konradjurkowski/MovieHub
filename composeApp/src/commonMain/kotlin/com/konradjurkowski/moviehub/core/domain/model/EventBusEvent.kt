package com.konradjurkowski.moviehub.core.domain.model

abstract class EventBusEvent

data class QrCodeScanned(val qrCode: String) : EventBusEvent()
