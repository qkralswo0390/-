package com.example.project

data class AirQualityResponse(
    val response: AirQualityInnerResponse
)

data class AirQualityInnerResponse(
    val body: AirQualityBody
)

data class AirQualityBody(
    val items: List<AirQualityItem>
)

data class AirQualityItem(
    val pm10Value: String?,
    val pm25Value: String?
)
