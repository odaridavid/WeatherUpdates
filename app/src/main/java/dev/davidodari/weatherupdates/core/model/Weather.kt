package dev.davidodari.weatherupdates.core.model

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val hourlyWeather: HourlyWeather,
    val currentWeather: CurrentWeather
)

data class HourlyWeather(val data: Pair<String, String>)

data class CurrentWeather(val temperature: String, val time: String)
