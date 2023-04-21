package dev.davidodari.weatherupdates.data

import dev.davidodari.weatherupdates.core.model.Weather
import dev.davidodari.weatherupdates.core.api.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val openMeteoService: OpenMeteoService,
) : WeatherRepository {
    override fun fetchWeatherData(): Flow<Result<Weather>> = flow {

        val response = openMeteoService.getWeatherData(latitude = 0.0, longitude = 0.0)

        // TODO Improve error handling
        if (response.isSuccessful && response.body() != null) {
            val weatherData = response.body()!!.toCoreModel()
            emit(Result.success(weatherData))
        } else {
            emit(Result.failure(Throwable("Error Occurred : ${response.errorBody()?.string()}")))
        }
    }.catch {
        emit(Result.failure(Throwable("Unexpected Error Occurred : $it")))
    }

}
