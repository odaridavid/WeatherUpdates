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
        @Query("hourly") hourly:String = "temperature_2m",
        @Query("temperature_unit") temperatureUnit: String = Units.METRIC.value,
        @Query("forecast_days") forecastDays: Int = 1,
        @Query("current_weather") includeCurrentWeather: Boolean = true
    ): Response<WeatherResponse>

}
