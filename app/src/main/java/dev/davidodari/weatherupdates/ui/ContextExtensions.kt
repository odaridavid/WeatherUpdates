package dev.davidodari.weatherupdates.ui

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import java.util.Locale

fun Context.getCityName(
    latitude: Double,
    longitude: Double,
    onAddressReceived: (Address) -> Unit
) {
    val geocoder = Geocoder(this, Locale.getDefault())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val geocoderListener = Geocoder.GeocodeListener { addresses ->
            if (addresses.isNotEmpty()) {
                onAddressReceived(addresses[0])
            }
        }
        geocoder.getFromLocation(latitude, longitude, 1, geocoderListener)
    } else {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses?.isNotEmpty() == true) {
            onAddressReceived(addresses[0])
        }
    }
}
