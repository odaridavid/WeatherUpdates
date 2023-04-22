package dev.davidodari.weatherupdates.data

import dev.davidodari.weatherupdates.core.model.CurrentWeather
import dev.davidodari.weatherupdates.core.model.HourlyWeather
import dev.davidodari.weatherupdates.core.model.Units
import dev.davidodari.weatherupdates.core.model.Weather
import dev.davidodari.weatherupdates.core.model.WeatherInfo
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

fun WeatherResponse.toCoreModel(): Weather = Weather(
    latitude = latitude,
    longitude = longitude,
    hourlyWeather = mapDatesToHourly(hourly),
    currentWeather = mapToCurrentWeather(currentWeather)
)

private fun mapToCurrentWeather(currentWeather: CurrentWeatherResponse): CurrentWeather =
    CurrentWeather(
        temperature = formatTemperatureValue(
            temperature = currentWeather.temperature,
            unit = Units.METRIC.value
        )
    )

private fun mapDatesToHourly(hourly: HourlyResponse): HourlyWeather {
    val weatherInfoList = mutableListOf<WeatherInfo>()
    for ((index, time) in hourly.times.withIndex()) {
        val temperature = hourly.temperatures[index]
        val formattedTemperature =
            formatTemperatureValue(temperature = temperature, unit = Units.METRIC.value)
        val formattedTime = formattedDateToHourlyTime(time)
        val weatherInfo = WeatherInfo(time = formattedTime, temperature = formattedTemperature)
        weatherInfoList.add(weatherInfo)
    }
    return HourlyWeather(data = weatherInfoList)
}

// TODO Fix zone for date
fun formattedDateToHourlyTime(time: String): String {
    val odt = OffsetDateTime.parse(time + "Z")
    val dtf = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    return dtf.format(odt)
}

private fun formatTemperatureValue(temperature: Float, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"

private fun getUnitSymbols(unit: String) = when (unit) {
    Units.METRIC.value -> Units.METRIC.tempLabel
    Units.IMPERIAL.value -> Units.IMPERIAL.tempLabel
    else -> "N/A"
}
