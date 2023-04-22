package dev.davidodari.weatherupdates.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("longitude") val longitude: Double,
    @SerialName("latitude") val latitude: Double,
    @SerialName("timezone") val timezone: String,
    @SerialName("hourly") val hourly: HourlyResponse,
    @SerialName("hourly_units") val hourlyUnits: HourlyUnitsResponse,
    @SerialName("current_weather") val currentWeather: CurrentWeatherResponse,
)

@Serializable
data class HourlyResponse(
    @SerialName("time") val times: List<String>,
    @SerialName("temperature_2m") val temperatures: List<Float>
)

@Serializable
data class HourlyUnitsResponse(
    @SerialName("time") val time: String,
    @SerialName("temperature_2m") val temperature: String
)

@Serializable
data class CurrentWeatherResponse(
    @SerialName("time") val time: String,
    @SerialName("temperature_2m") val temperature: Float
)
