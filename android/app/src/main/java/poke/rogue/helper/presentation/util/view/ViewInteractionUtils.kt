package poke.rogue.helper.presentation.util.view

import android.view.View
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

inline fun View.setOnSingleClickListener(
    delay: Long = 500L,
    crossinline block: (View) -> Unit
) {
    val singleEventHandler: SingleEventHandler = DefaultSingleEventHandler(delay.milliseconds)
    setOnClickListener { view ->
        singleEventHandler.handle {
            block(view)
        }
    }
}

fun interface SingleEventHandler {
    fun handle(event: () -> Unit)
}

class DefaultSingleEventHandler(private val throttleDuration: Duration = 500.milliseconds) :
    SingleEventHandler {
    private val currentTime: TimeMark get() = TimeSource.Monotonic.markNow()
    private lateinit var lastEventTime: TimeMark

    override fun handle(event: () -> Unit) {
        if (::lastEventTime.isInitialized.not() || (lastEventTime + throttleDuration).hasPassedNow()) {
            event()
        }
        lastEventTime = currentTime
    }
}