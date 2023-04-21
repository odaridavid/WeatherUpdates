package dev.davidodari.weatherupdates

import dev.davidodari.weatherupdates.core.api.WeatherRepository
import dev.davidodari.weatherupdates.core.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherRepository : WeatherRepository {

    var isSuccessful = true
    override fun fetchWeatherData(): Flow<Result<Weather>> = flow {
        if (isSuccessful){
            emit(Result.success(fakeSuccessMappedWeatherResponse))
        }else{
            emit(Result.failure(Throwable("An Error Occurred")))
        }
    }

}
