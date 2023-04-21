package dev.davidodari.weatherupdates

import dev.davidodari.weatherupdates.core.model.CurrentWeather
import dev.davidodari.weatherupdates.core.model.HourlyWeather
import dev.davidodari.weatherupdates.core.model.Weather
import dev.davidodari.weatherupdates.data.CurrentWeatherResponse
import dev.davidodari.weatherupdates.data.HourlyResponse
import dev.davidodari.weatherupdates.data.HourlyUnitsResponse
import dev.davidodari.weatherupdates.data.WeatherResponse


val fakeSuccessWeatherResponse = WeatherResponse(
    longitude = 8.9,
    latitude = 10.11,
    timezone = "aeque",
    hourly = HourlyResponse(
        time = "",
        temperature = 12.13f,
    ),
    hourlyUnits = HourlyUnitsResponse(
        temperature = "",
    ),
    currentWeather = CurrentWeatherResponse(
        time = "",
        temperature = 14.15f,
    )

)

val fakeSuccessMappedWeatherResponse = Weather(
    latitude = 0.0,
    longitude = 0.0,
    hourlyWeather = HourlyWeather(data = Pair("", "")),
    currentWeather = CurrentWeather(
        temperature = "",
        time = ""
    )
)
