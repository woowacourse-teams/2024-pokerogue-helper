package poke.rogue.helper.presentation.util.view

import android.view.View
import androidx.databinding.BindingAdapter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

// TODO : move to ui/intereaction/SingleClick.kt

@BindingAdapter("duration", "onSingleClick", requireAll = false)
fun View.setOnSingleClickListener(
    duration: Int = 500,
    listener: View.OnClickListener?,
) {
    val throttleDuration = if (duration == 0) 500 else duration
    val singleEventHandler: SingleEventHandler =
        DefaultSingleEventHandler(throttleDuration.milliseconds)
    setOnClickListener { view ->
        singleEventHandler.handle {
            listener?.onClick(view)
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
