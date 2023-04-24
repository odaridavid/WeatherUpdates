package dev.davidodari.weatherupdates.ui.home

sealed class HomeScreenIntent {
    object LoadWeatherData : HomeScreenIntent()

    data class OnCityNameReceived(val cityName: String) : HomeScreenIntent()

    object CancelWeatherDataPolling : HomeScreenIntent()
}
