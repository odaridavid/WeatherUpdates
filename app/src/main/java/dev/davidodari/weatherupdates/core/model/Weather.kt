package dev.davidodari.weatherupdates.core.model

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val hourlyWeather: HourlyWeather,
    val currentWeather: CurrentWeather
)

data class HourlyWeather(val data: List<WeatherInfo>)
data class CurrentWeather(val temperature: String)

data class WeatherInfo(val temperature: String, val time: String)
