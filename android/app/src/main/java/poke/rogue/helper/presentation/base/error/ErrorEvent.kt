package poke.rogue.helper.presentation.base.error

sealed class ErrorEvent(val msg: String? = null) {
    data class HttpException(val error: Throwable) : ErrorEvent(error.message)

    data object NetworkException : ErrorEvent()

    data class UnknownError(val error: Throwable) : ErrorEvent(error.message)
}
