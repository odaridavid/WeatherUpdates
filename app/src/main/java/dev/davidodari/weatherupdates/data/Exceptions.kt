package dev.davidodari.weatherupdates.data

import androidx.annotation.StringRes

// TODO Better naming, exception could be from local or wherever else
sealed class ApiException(@StringRes open val messageId: Int) {

}

data class UnauthorizedException(@StringRes override val messageId: Int) : ApiException(messageId)

data class ClientException(@StringRes override val messageId: Int) : ApiException(messageId)

data class ServerException(@StringRes override val messageId: Int) : ApiException(messageId)

data class ConnectionException(@StringRes override val messageId: Int) : ApiException(messageId)

data class GenericException(@StringRes override val messageId: Int) : ApiException(messageId)
