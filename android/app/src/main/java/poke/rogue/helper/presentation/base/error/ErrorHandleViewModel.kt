package poke.rogue.helper.presentation.base.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.exception.HttpException
import poke.rogue.helper.data.exception.NetworkException
import poke.rogue.helper.data.exception.PokeException
import poke.rogue.helper.data.exception.UnknownException
import poke.rogue.helper.presentation.util.event.RefreshEventBus

abstract class ErrorHandleViewModel(private val logger: AnalyticsLogger) : ViewModel() {
    private val _commonErrorEvent = MutableSharedFlow<ErrorEvent>()
    val commonErrorEvent: SharedFlow<ErrorEvent> = _commonErrorEvent.asSharedFlow()

    val refreshEvent: Flow<Unit> = RefreshEventBus.event

    protected open val errorHandler =
        CoroutineExceptionHandler { _, throwable ->
            logger.logError(throwable, throwable.message)
            handlePokemonError(throwable)
        }

    protected fun handlePokemonError(throwable: Throwable) {
        if (throwable !is PokeException) {
            logger.logError(throwable, "Poke Exception 이 아닌 에러 발생")
            emitErrorEvent(ErrorEvent.UnknownError(throwable))
            return
        }
        when (throwable) {
            is NetworkException -> emitErrorEvent(ErrorEvent.NetworkException)
            is HttpException -> emitErrorEvent(ErrorEvent.HttpException(throwable))
            is UnknownException -> emitErrorEvent(ErrorEvent.UnknownError(throwable))
        }
    }

    private fun emitErrorEvent(errorEvent: ErrorEvent) {
        viewModelScope.launch {
            _commonErrorEvent.emit(errorEvent)
        }
    }
}
