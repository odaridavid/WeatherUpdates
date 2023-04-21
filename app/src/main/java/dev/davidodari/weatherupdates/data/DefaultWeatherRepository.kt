package dev.davidodari.weatherupdates.data

import dev.davidodari.weatherupdates.R
import dev.davidodari.weatherupdates.core.model.Weather
import dev.davidodari.weatherupdates.core.api.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val openMeteoService: OpenMeteoService,
) : WeatherRepository {
    override fun fetchWeatherData(): Flow<ApiResult<Weather>> = flow {

        // TODO Load data every 10 seconds.
        val currentCoordinatesIndex = 0
        val response = openMeteoService.getWeatherData(
            latitude = coordinates[currentCoordinatesIndex].latitude,
            longitude = coordinates[currentCoordinatesIndex].longitude
        )

        // TODO load daily data
        if (response.isSuccessful && response.body() != null) {
            val weatherData = response.body()!!.toCoreModel()
            emit(ApiResult.Success(data = weatherData))
        } else {
            // TODO Push raw error to a dashboard
            val error = response.errorBody()?.string()

            val mappedException = mapResponseCodeToExceptions(response)
            emit(ApiResult.Error(messageId = mappedException.messageId))
        }
    }.catch {
        val mappedException = when (it) {
            is IOException -> ConnectionException(R.string.error_connection)
            else -> GenericException(R.string.error_generic)
        }
        // TODO Push raw error to a dashboard
        emit(ApiResult.Error(mappedException.messageId))
    }

    private fun mapResponseCodeToExceptions(response: Response<WeatherResponse>): ApiException{
        val mappedException = when (response.code()) {
            HTTP_UNAUTHORIZED -> UnauthorizedException(R.string.error_unauthorized)
            in 400..499 -> ClientException(R.string.error_client)
            in 500..600 -> ServerException(R.string.error_server)
            else -> GenericException(R.string.error_generic)
        }
        return mappedException
    }

}
