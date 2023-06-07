package dev.davidodari.weatherupdates

import app.cash.turbine.test
import com.google.common.truth.Truth
import dev.davidodari.weatherupdates.core.api.WeatherRepository
import dev.davidodari.weatherupdates.data.DefaultWeatherRepository
import dev.davidodari.weatherupdates.data.OpenMeteoService
import dev.davidodari.weatherupdates.data.PollingService
import dev.davidodari.weatherupdates.ui.home.HomeScreenIntent
import dev.davidodari.weatherupdates.ui.home.HomeScreenViewState
import dev.davidodari.weatherupdates.ui.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelIntegrationTest {

    @MockK
    val mockOpenMeteoService = mockk<OpenMeteoService>(relaxed = true)

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `when fetching weather data is successful, then display correct data`() = runBlocking {
        coEvery {
            mockOpenMeteoService.getWeatherData(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns Response.success(fakeSuccessWeatherResponse)

        val repository = DefaultWeatherRepository(openMeteoService = mockOpenMeteoService,pollingService = PollingService)

        val viewModel = createViewModel(repository)

        viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

        val expectedState = HomeScreenViewState(
            weather = fakeSuccessMappedWeatherResponse,
            isLoading = false,
            error = null
        )

        viewModel.state.test {
            awaitItem().also { state ->
                Truth.assertThat(state).isEqualTo(expectedState)
            }
        }
    }

    @Test
    fun `when fetching weather data is unsuccessful, then display correct error state`() =
        runBlocking {
            coEvery {
                mockOpenMeteoService.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Response.error(
                404,
                "{}".toResponseBody()
            )

            val repository = DefaultWeatherRepository(openMeteoService = mockOpenMeteoService,pollingService = PollingService)

            val viewModel = createViewModel(repository)

            viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

            val expectedState = HomeScreenViewState(
                weather = null,
                isLoading = false,
                error = R.string.error_client
            )

            viewModel.state.test {
                awaitItem().also { state ->
                    Truth.assertThat(state).isEqualTo(expectedState)
                }
            }
        }

    @Test
    fun `when we init the screen, then update the state`() = runBlocking {
        val repository = DefaultWeatherRepository(openMeteoService = mockOpenMeteoService, pollingService = PollingService)

        val viewModel = createViewModel(repository)

        viewModel.state.test {
            awaitItem().also { state ->
                assert(state.isLoading)
            }
        }
    }

    @Test
    fun `when the app is stopped, then polling stops`() {
        val repository = mockk<WeatherRepository>()

        val viewModel = createViewModel(repository, pollingService = PollingService)

        viewModel.processIntent(HomeScreenIntent.CancelWeatherDataPolling)

        Truth.assertThat(PollingService.isPolling()).isFalse()
    }

    @Test
    fun `when we receive a city name, the state is updated with it`() = runTest {
        val weatherRepository = mockk<WeatherRepository>()

        val viewModel = createViewModel(weatherRepository)

        val expectedState = HomeScreenViewState(
            cityName = "Paradise",
            isLoading = true
        )

        viewModel.processIntent(HomeScreenIntent.DisplayCityName(cityName = "Paradise"))

        viewModel.state.test {
            awaitItem().also { state ->
                Truth.assertThat(state).isEqualTo(expectedState)
            }
        }
    }

    private fun createViewModel(
        repository: WeatherRepository,
        pollingService: PollingService = PollingService
    ): HomeViewModel = HomeViewModel(
        weatherRepository = repository,
        pollingService = pollingService
    )
}
