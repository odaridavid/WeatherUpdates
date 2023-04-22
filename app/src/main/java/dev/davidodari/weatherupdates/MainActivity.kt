package dev.davidodari.weatherupdates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.davidodari.weatherupdates.ui.home.HomeScreenIntent
import dev.davidodari.weatherupdates.ui.home.HomeViewModel
import dev.davidodari.weatherupdates.ui.theme.WeatherAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherAppDestinationsConfig(
                        navController = rememberNavController(),
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.processIntent(HomeScreenIntent.LoadWeatherData)
    }

    override fun onStop() {
        super.onStop()
        homeViewModel.processIntent(HomeScreenIntent.CancelWeatherDataPolling)
    }
}

