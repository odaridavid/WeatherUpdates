package dev.davidodari.weatherupdates.ui.home

sealed class HomeScreenIntent {
    object LoadWeatherData : HomeScreenIntent()

    object CancelWeatherDataPolling: HomeScreenIntent()
}
