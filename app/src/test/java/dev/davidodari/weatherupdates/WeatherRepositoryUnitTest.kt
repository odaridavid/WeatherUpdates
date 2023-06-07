package dev.davidodari.weatherupdates

import app.cash.turbine.test
import com.google.common.truth.Truth
import dev.davidodari.weatherupdates.core.api.WeatherRepository
import dev.davidodari.weatherupdates.data.Result
import dev.davidodari.weatherupdates.data.DefaultWeatherRepository
import dev.davidodari.weatherupdates.data.ErrorType
import dev.davidodari.weatherupdates.data.OpenMeteoService
import dev.davidodari.weatherupdates.data.PollingService
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryUnitTest {

    @MockK
    val mockOpenMeteoService = mockk<OpenMeteoService>(relaxed = true)

    @Test
    fun `when we fetch weather data successfully, then a successfully mapped result is emitted`() =
        runTest {
            val weatherRepository = createWeatherRepository()

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

            weatherRepository.fetchWeatherData().test {
                awaitItem().also { result ->
                    Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
                    Truth.assertThat((result as Result.Success).data).isEqualTo(
                        fakeSuccessMappedWeatherResponse
                    )
                }
            }
        }

    @Test
    fun `when we fetch weather data and a server error occurs, then a server error is emitted`() =
        runTest {
            val weatherRepository = createWeatherRepository()

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
                505,
                "{}".toResponseBody()
            )

            weatherRepository.fetchWeatherData().test {
                awaitItem().also { result ->
                    Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                    Truth.assertThat((result as Result.Error).errorType)
                        .isEqualTo(ErrorType.SERVER)
                }
            }
        }

    @Test
    fun `when we fetch weather data and a client error occurs, then a client error is emitted`() =
        runTest {
            val weatherRepository = createWeatherRepository()

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

            weatherRepository.fetchWeatherData().test {
                awaitItem().also { result ->
                    Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                    Truth.assertThat((result as Result.Error).errorType)
                        .isEqualTo(ErrorType.CLIENT)
                }
            }
        }

    @Test
    fun `when we fetch weather data and a unauthorized error occurs, then unauthorized error is emitted`() =
        runTest {
            val weatherRepository = createWeatherRepository()

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
                401,
                "{}".toResponseBody()
            )

            weatherRepository.fetchWeatherData().test {
                awaitItem().also { result ->
                    Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                    Truth.assertThat((result as Result.Error).errorType)
                        .isEqualTo(ErrorType.UNAUTHORIZED)
                }
            }
        }

    @Test
    fun `when we fetch weather data and an error occurs, then a generic error is emitted`() =
        runTest {
            val weatherRepository = createWeatherRepository()

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
                909,
                "{}".toResponseBody()
            )

            weatherRepository.fetchWeatherData().test {
                awaitItem().also { result ->
                    Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                    Truth.assertThat((result as Result.Error).errorType)
                        .isEqualTo(ErrorType.GENERIC)
                }
            }
        }

    @Test
    fun `when we fetch weather data and an IOException is thrown, then a connection error is emitted`() =
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
            } throws IOException()

            val weatherRepository = createWeatherRepository()

            weatherRepository.fetchWeatherData().test {
                awaitItem().also { result ->
                    Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                    Truth.assertThat((result as Result.Error).errorType)
                        .isEqualTo(ErrorType.IO_CONNECTION)
                }
                awaitComplete()
            }
        }

    @Test
    fun `when we fetch weather data and an unknown Exception is thrown, then a generic error is emitted`() =
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
            } throws Exception()

            val weatherRepository = createWeatherRepository()

            weatherRepository.fetchWeatherData().test {
                awaitItem().also { result ->
                    Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                    Truth.assertThat((result as Result.Error).errorType)
                        .isEqualTo(ErrorType.GENERIC)
                }
                awaitComplete()
            }
        }

    @Test
    fun `when we receive an error, then stop polling`() = runTest {
        coEvery {
            mockOpenMeteoService.getWeatherData(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } throws Exception()

        val weatherRepository = createWeatherRepository()

        weatherRepository.fetchWeatherData().test {
            awaitItem()
            awaitComplete()
        }

        Truth.assertThat(PollingService.isPolling()).isFalse()
    }

    @Test
    fun `when we fetch data, then start polling`() = runTest {
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

        val weatherRepository = createWeatherRepository()

        weatherRepository.fetchWeatherData().test {
            awaitItem()
        }

        Truth.assertThat(PollingService.isPolling()).isTrue()
    }

    private fun createWeatherRepository(): WeatherRepository = DefaultWeatherRepository(
        openMeteoService = mockOpenMeteoService,
        pollingService = PollingService
    )

}
