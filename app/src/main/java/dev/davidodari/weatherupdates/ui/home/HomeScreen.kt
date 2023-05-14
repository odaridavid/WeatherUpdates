package dev.davidodari.weatherupdates.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.davidodari.weatherupdates.R
import dev.davidodari.weatherupdates.core.model.CurrentWeather
import dev.davidodari.weatherupdates.core.model.HourlyWeather
import dev.davidodari.weatherupdates.core.model.WeatherInfo
import dev.davidodari.weatherupdates.ui.getCityName
import dev.davidodari.weatherupdates.ui.theme.Sizing
import dev.davidodari.weatherupdates.ui.theme.sizing
import dev.davidodari.weatherupdates.ui.theme.weight

@Composable
fun HomeScreen(
    state: HomeScreenViewState,
    onTryAgainClicked: () -> Unit,
    onAddressReceived: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        state.weather?.let { weather ->
            LocalContext.current.getCityName(
                latitude = weather.latitude,
                longitude = weather.longitude
            ) { address ->
                onAddressReceived(address.locality)
            }
        }

        HomeTopBar(cityName = state.cityName)

        if (state.isLoading) {
            Spacer(modifier = Modifier.weight(MaterialTheme.weight.half))
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(MaterialTheme.sizing.medium)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(MaterialTheme.weight.half))
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.weight(MaterialTheme.weight.half))
            ErrorContent(
                errorId = state.error,
                modifier = Modifier.padding(MaterialTheme.sizing.medium)
            ) {
                onTryAgainClicked()
            }
            Spacer(modifier = Modifier.weight(MaterialTheme.weight.half))
        }

        state.weather?.currentWeather?.let { currentWeather ->
            CurrentWeatherWidget(currentWeather)
        }
        state.weather?.hourlyWeather?.let { hourlyWeather ->
            HourlyWeatherWidget(hourlyWeather)
        }
    }
}

@Composable
private fun HourlyWeatherWidget(hourlyWeather: HourlyWeather) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.sizing.medium)
    ) {
        Text(
            text = stringResource(R.string.home_today_forecast_title),
            style = MaterialTheme.typography.headlineSmall
        )
        for (weatherInfo in hourlyWeather.data) {
            HourlyWeatherRow(hourlyWeather = weatherInfo)
        }
    }
}

@Composable
fun HourlyWeatherRow(hourlyWeather: WeatherInfo) {
    Row(
        modifier = Modifier
            .padding(MaterialTheme.sizing.small)
            .fillMaxWidth()
    ) {
        Text(text = hourlyWeather.time, modifier = Modifier.padding(MaterialTheme.sizing.small))
        Spacer(modifier = Modifier.weight(MaterialTheme.weight.full))
        Text(
            text = hourlyWeather.temperature,
            modifier = Modifier.padding(MaterialTheme.sizing.small)
        )
    }
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather) {
    Column {
        Text(
            text = stringResource(R.string.home_title_currently),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.sizing.medium)
                .padding(vertical = MaterialTheme.sizing.small),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = currentWeather.temperature,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.sizing.medium)
                .padding(vertical = MaterialTheme.sizing.small),
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
private fun HomeTopBar(cityName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.sizing.small)
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(MaterialTheme.sizing.small)
        )
    }
}

@Composable
private fun ErrorContent(errorId: Int, modifier: Modifier, onTryAgainClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = errorId),
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = { onTryAgainClicked() },
            modifier = Modifier
                .padding(MaterialTheme.sizing.medium)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.home_error_try_again),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
