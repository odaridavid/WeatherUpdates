package dev.davidodari.weatherupdates

import dev.davidodari.weatherupdates.core.model.CurrentWeather
import dev.davidodari.weatherupdates.core.model.HourlyWeather
import dev.davidodari.weatherupdates.core.model.Weather
import dev.davidodari.weatherupdates.core.model.WeatherInfo
import dev.davidodari.weatherupdates.data.CurrentWeatherResponse
import dev.davidodari.weatherupdates.data.HourlyResponse
import dev.davidodari.weatherupdates.data.HourlyUnitsResponse
import dev.davidodari.weatherupdates.data.WeatherResponse


val fakeSuccessWeatherResponse = WeatherResponse(
    longitude = 8.9,
    latitude = 10.11,
    timezone = "",
    hourly = HourlyResponse(
        times = listOf("2023-04-22T12:00"),
        temperatures = listOf(13.0f),
    ),
    hourlyUnits = HourlyUnitsResponse(
        time = "iso8601",
        temperature = "°C",
    ),
    currentWeather = CurrentWeatherResponse(
        time = "2023-04-22T15:00",
        temperature = 14.15f,
    )

)

val fakeSuccessMappedWeatherResponse = Weather(
    longitude = 8.9,
    latitude = 10.11,
    hourlyWeather = HourlyWeather(data = listOf(WeatherInfo(temperature = "13°C", time = "12:00"))),
    currentWeather = CurrentWeather(
        temperature = "14°C",
    )
)
