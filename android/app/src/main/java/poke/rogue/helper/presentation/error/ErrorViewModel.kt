package poke.rogue.helper.presentation.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

// TODO 수정 : analyticsLogger() -> analyticsLogger
abstract class ErrorViewModel(logger: AnalyticsLogger = analyticsLogger()) : ViewModel() {
    private val _commonErrorEvent = MutableSharedFlow<ErrorEvent>()
    val commonErrorEvent: SharedFlow<ErrorEvent> = _commonErrorEvent.asSharedFlow()

    protected open val errorHandler =
        CoroutineExceptionHandler { _, throwable ->
            logger.logError(throwable, throwable.message)
            when (throwable) {
                is HttpException -> emitErrorEvent(ErrorEvent.HttpException(throwable))
                is ConnectException, is IOException -> emitErrorEvent(ErrorEvent.NetworkConnection)
                else -> emitErrorEvent(ErrorEvent.UnknownError(throwable))
            }
        }

    protected fun emitErrorEvent(errorEvent: ErrorEvent) {
        viewModelScope.launch {
            _commonErrorEvent.emit(errorEvent)
        }
    }
}

sealed class ErrorEvent(val msg: String? = null) {
    data class HttpException(val error: Throwable) : ErrorEvent(error.message)

    data object NetworkConnection : ErrorEvent()

    data class UnknownError(val error: Throwable) : ErrorEvent(error.message)
}
