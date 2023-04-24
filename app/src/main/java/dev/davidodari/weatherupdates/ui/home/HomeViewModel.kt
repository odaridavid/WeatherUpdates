package dev.davidodari.weatherupdates.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.davidodari.weatherupdates.core.api.WeatherRepository
import dev.davidodari.weatherupdates.core.model.Weather
import dev.davidodari.weatherupdates.data.ApiResult
import dev.davidodari.weatherupdates.data.PollingService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val pollingService: PollingService
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenViewState(isLoading = true))
    val state: StateFlow<HomeScreenViewState> = _state.asStateFlow()

    fun processIntent(homeScreenIntent: HomeScreenIntent) {
        when (homeScreenIntent) {
            is HomeScreenIntent.LoadWeatherData -> {
                viewModelScope.launch {
                    weatherRepository.fetchWeatherData().collect { result ->
                        processResult(result)
                    }
                }
            }

            is HomeScreenIntent.CancelWeatherDataPolling -> {
                pollingService.stopPolling()
            }

            is HomeScreenIntent.OnCityNameReceived -> {
                setState { copy(cityName = homeScreenIntent.cityName) }
            }
        }
    }

    private fun processResult(result: ApiResult<Weather>) {
        when (result) {
            is ApiResult.Success -> {
                val weatherData = result.data
                setState {
                    copy(
                        weather = weatherData,
                        isLoading = false,
                        error = null
                    )
                }
            }

            is ApiResult.Error -> {
                setState {
                    copy(
                        isLoading = false,
                        error = result.messageId
                    )
                }
            }
        }
    }

    private fun setState(stateReducer: HomeScreenViewState.() -> HomeScreenViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }

}

data class HomeScreenViewState(
    val cityName: String = "-",
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)
