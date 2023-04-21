package dev.davidodari.weatherupdates.ui.home

sealed class HomeScreenIntent {
    object LoadWeatherData : HomeScreenIntent()
}
