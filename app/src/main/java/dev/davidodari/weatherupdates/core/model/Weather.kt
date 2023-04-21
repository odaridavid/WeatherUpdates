package dev.davidodari.weatherupdates.core.model

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val hourlyWeather: HourlyWeather,
    val currentWeather: WeatherInfo,
    val dailyWeather: DailyWeather
)

data class HourlyWeather(val data: Pair<String, String>)
data class DailyWeather(val data: Pair<String, WeatherInfo>)
// TODO Fix this data class to be compatible with everything.
data class WeatherInfo(val temperature: String, val time: String)

data class Error(val errorMessage: String)
