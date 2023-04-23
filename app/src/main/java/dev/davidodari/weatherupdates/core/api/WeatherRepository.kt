package dev.davidodari.weatherupdates.core.api

import dev.davidodari.weatherupdates.core.model.Weather
import dev.davidodari.weatherupdates.data.ApiResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeatherData(): Flow<ApiResult<Weather>>
}
