package dev.davidodari.weatherupdates.core.model

enum class Units(val value: String, val tempLabel: String) {
    METRIC("celsius","°C"),
    IMPERIAL("fahrenheit","°F"),
}
