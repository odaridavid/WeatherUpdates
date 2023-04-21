package dev.davidodari.weatherupdates

import dev.davidodari.weatherupdates.core.api.WeatherRepository
import dev.davidodari.weatherupdates.data.DefaultWeatherRepository
import dev.davidodari.weatherupdates.data.OpenMeteoService
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class WeatherRepositoryUnitTest {

    @MockK
    val mockOpenMeteoService = mockk<OpenMeteoService>(relaxed = true)

    @Test
    fun `when we fetch weather data successfully, then a successfully mapped result is emitted`() {
        TODO()
    }


    @Test
    fun `when we fetch weather data and an error occurs, then an error is emitted`()  {
        TODO()
    }

    private fun createWeatherRepository(): WeatherRepository = DefaultWeatherRepository(
        openMeteoService = mockOpenMeteoService
    )

}
