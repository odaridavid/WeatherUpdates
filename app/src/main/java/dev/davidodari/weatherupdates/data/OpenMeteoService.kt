package dev.davidodari.weatherupdates.data

import dev.davidodari.weatherupdates.core.model.Units
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {

    @GET("/v1/forecast")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("temperature_unit") temperatureUnit: String = Units.METRIC.value,
        @Query("forecast_days") forecastDays: Int = 7
    ): Response<WeatherResponse>

}
