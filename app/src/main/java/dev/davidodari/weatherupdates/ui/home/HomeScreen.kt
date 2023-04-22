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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.davidodari.weatherupdates.R
import dev.davidodari.weatherupdates.core.model.CurrentWeather
import dev.davidodari.weatherupdates.core.model.HourlyWeather
import dev.davidodari.weatherupdates.core.model.WeatherInfo
import dev.davidodari.weatherupdates.ui.theme.Sizing

//TODO Fix my composition local problem with material extending sizing
@Composable
fun HomeScreen(
    state: HomeScreenViewState,
    onTryAgainClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        //TODO Convert location coordinates to city name
        HomeTopBar(cityName = "-")

        if (state.isLoading) {
            Spacer(modifier = Modifier.weight(0.5f))
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(Sizing.medium)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.weight(0.5f))
            ErrorContent(
                errorId = state.error,
                modifier = Modifier.padding(Sizing.medium)
            ) {
                onTryAgainClicked()
            }
            Spacer(modifier = Modifier.weight(0.5f))
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
            .padding(Sizing.medium)
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
            .padding(Sizing.small)
            .fillMaxWidth()
    ) {
        Text(text = hourlyWeather.time, modifier = Modifier.padding(Sizing.small))
        Spacer(modifier = Modifier.weight(1.0f))
        Text(text = hourlyWeather.temperature, modifier = Modifier.padding(Sizing.small))
    }
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather) {
    Column {
        Text(
            text = stringResource(R.string.home_title_currently),
            modifier = Modifier
                .padding(horizontal = Sizing.medium)
                .padding(vertical = Sizing.small),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = currentWeather.temperature,
            modifier = Modifier
                .padding(horizontal = Sizing.medium)
                .padding(vertical = Sizing.small),
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
private fun HomeTopBar(cityName: String) {
    Row(
        modifier = Modifier
            .padding(Sizing.medium)
            .fillMaxWidth()
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(Sizing.small)
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
                .padding(Sizing.medium)
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
