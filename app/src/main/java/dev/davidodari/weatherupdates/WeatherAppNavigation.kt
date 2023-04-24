package dev.davidodari.weatherupdates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.davidodari.weatherupdates.ui.home.HomeScreen
import dev.davidodari.weatherupdates.ui.home.HomeScreenIntent
import dev.davidodari.weatherupdates.ui.home.HomeViewModel

@Composable
fun WeatherAppDestinationsConfig(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
) {
    NavHost(navController = navController, startDestination = Destinations.HOME.route) {
        composable(Destinations.HOME.route) {
            val state = homeViewModel
                .state
                .collectAsState()
                .value

            HomeScreen(
                state = state,
                onTryAgainClicked = { homeViewModel.processIntent(HomeScreenIntent.LoadWeatherData) },
                onAddressReceived = {homeViewModel.processIntent(HomeScreenIntent.OnCityNameReceived(cityName = it))}
            )
        }
    }
}

enum class Destinations(val route: String) {
    HOME("home")
}
