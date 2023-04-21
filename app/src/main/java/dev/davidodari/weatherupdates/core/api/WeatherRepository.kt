package dev.davidodari.weatherupdates.core.api

import dev.davidodari.weatherupdates.core.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeatherData(): Flow<Result<Weather>>
}
