package poke.rogue.helper.presentation.util.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object RefreshEventBus {
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob())
    private val _event = MutableEventFlow<Unit>(capacity = 1)
    val event: EventFlow<Unit> = _event.asEventFlow()

    fun send() {
        coroutineScope.launch {
            _event.emit(Unit)
        }
    }
}