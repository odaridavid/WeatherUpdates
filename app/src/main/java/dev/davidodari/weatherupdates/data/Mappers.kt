package dev.davidodari.weatherupdates.data

import dev.davidodari.weatherupdates.core.model.Units
import dev.davidodari.weatherupdates.core.model.Weather
import kotlin.math.roundToInt

fun WeatherResponse.toCoreModel(): Weather = TODO("Map response")

private fun formatTemperatureValue(temperature: Float, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"

private fun getUnitSymbols(unit: String) = when (unit) {
    Units.METRIC.value -> Units.METRIC.tempLabel
    Units.IMPERIAL.value -> Units.IMPERIAL.tempLabel
    else -> "N/A"
}
