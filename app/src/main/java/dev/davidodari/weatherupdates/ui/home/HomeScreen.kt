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
import dev.davidodari.weatherupdates.R
import dev.davidodari.weatherupdates.ui.theme.sizing

// TODO Fix my composition local problem with material extending sizing
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
        // TODO Convert location coordinates to city name
        HomeTopBar(cityName = "-")

        if (state.isLoading) {
            Spacer(modifier = Modifier.weight(0.5f))
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(MaterialTheme.sizing.medium)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.weight(0.5f))
            ErrorText(
                error = state.error,
                modifier = Modifier.padding(MaterialTheme.sizing.medium)
            ) {
                onTryAgainClicked()
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
        // todo display data
    }
}

@Composable
private fun HomeTopBar(cityName: String) {
    Row(
        modifier = Modifier
            .padding(MaterialTheme.sizing.medium)
            .fillMaxWidth()
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(MaterialTheme.sizing.small)
        )
    }
}

@Composable
private fun ErrorText(error: Throwable, modifier: Modifier, onTryAgainClicked: () -> Unit) {
    Text(
        text = error.message ?: stringResource(id = R.string.home_error_occurred_generic),
        textAlign = TextAlign.Center,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium
    )
    Button(onClick = { onTryAgainClicked() }) {
        Text(
            text = stringResource(R.string.home_error_try_again),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}
