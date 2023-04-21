package dev.davidodari.weatherupdates.data

import androidx.annotation.StringRes

sealed class ApiResult<T>() {
    data class Success<T>(val data: T) : ApiResult<T>()

    data class Error<T>(@StringRes val messageId: Int) : ApiResult<T>()
}
