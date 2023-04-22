package dev.davidodari.weatherupdates.data

import android.util.Log
import dev.davidodari.weatherupdates.R
import dev.davidodari.weatherupdates.core.api.WeatherRepository
import dev.davidodari.weatherupdates.core.model.ApiException
import dev.davidodari.weatherupdates.core.model.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val openMeteoService: OpenMeteoService,
) : WeatherRepository {

    private var isPolling: AtomicBoolean = AtomicBoolean(true)
    private val TEN_SECONDS = 10_000L
    override fun fetchWeatherData(): Flow<ApiResult<Weather>> = flow {
        startPolling()
        var currentWeatherCoordinatesIndex = 0
        while (isPolling.get()) {
            val coordinate = coordinates[currentWeatherCoordinatesIndex]
            fetchFromApi(coordinate = coordinate)
            if (currentWeatherCoordinatesIndex == coordinates.lastIndex) {
                currentWeatherCoordinatesIndex = 0
            } else {
                currentWeatherCoordinatesIndex += 1
            }

            delay(TEN_SECONDS)
        }
    }.catch { throwable ->
        stopPolling()
        val mappedException = when (throwable) {
            is IOException -> ApiException.ConnectionException(R.string.error_connection)
            else -> ApiException.GenericException(R.string.error_generic)
        }
        emit(ApiResult.Error(mappedException.messageId))
    }

    override fun startPolling() {
        Log.i("Poling", "Started")
        isPolling.compareAndSet(false, true)
    }

    override fun stopPolling() {
        Log.i("Poling", "Stopped")
        isPolling.compareAndSet(true, false)
    }

    private suspend fun FlowCollector<ApiResult<Weather>>.fetchFromApi(coordinate: Coordinate) {
        val response = openMeteoService.getWeatherData(
            latitude = coordinate.latitude,
            longitude = coordinate.longitude
        )

        if (response.isSuccessful && response.body() != null) {
            val weatherData = response.body()!!.toCoreModel()
            emit(ApiResult.Success(data = weatherData))
        } else {
            stopPolling()
            val mappedException = mapResponseCodeToExceptions(response)
            emit(ApiResult.Error(messageId = mappedException.messageId))
        }
    }

    private fun mapResponseCodeToExceptions(response: Response<WeatherResponse>): ApiException {
        val mappedException = when (response.code()) {
            HTTP_UNAUTHORIZED -> ApiException.UnauthorizedException(R.string.error_unauthorized)
            in 400..499 -> ApiException.ClientException(R.string.error_client)
            in 500..600 -> ApiException.ServerException(R.string.error_server)
            else -> ApiException.GenericException(R.string.error_generic)
        }
        return mappedException
    }

}
