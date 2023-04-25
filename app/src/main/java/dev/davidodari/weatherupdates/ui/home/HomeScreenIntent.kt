package dev.davidodari.weatherupdates.ui.home

sealed class HomeScreenIntent {
    object LoadWeatherData : HomeScreenIntent()

    data class DisplayCityName(val cityName: String) : HomeScreenIntent()

    object CancelWeatherDataPolling : HomeScreenIntent()
}
