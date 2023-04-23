package dev.davidodari.weatherupdates.data

import java.util.concurrent.atomic.AtomicBoolean

object PollingService {

    private var _isPolling: AtomicBoolean = AtomicBoolean(true)

    fun isPolling() = _isPolling.get()

    fun startPolling() {
        _isPolling.compareAndSet(false, true)
    }

    fun stopPolling() {
        _isPolling.compareAndSet(true, false)
    }
}
