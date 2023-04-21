package dev.davidodari.weatherupdates.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

object Sizing {
    val large = 32.dp
    val medium = 16.dp
    val small = 8.dp
    val extraSmall = 4.dp
    val none = 0.dp
}

val MaterialTheme.sizing
    get() = Sizing
