package com.example.project

data class WeatherResponse(
    val response: InnerResponse
)

data class InnerResponse(
    val body: Body
)

data class Body(
    val items: Items
)

data class Items(
    val item: List<WeatherItem>
)

data class WeatherItem(
    val category: String,
    val obsrValue: String
)
