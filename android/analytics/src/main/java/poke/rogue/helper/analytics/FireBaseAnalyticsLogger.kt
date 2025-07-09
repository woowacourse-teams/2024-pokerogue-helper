package poke.rogue.helper.analytics

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

internal object FireBaseAnalyticsLogger : AnalyticsLogger {
    override fun logEvent(event: AnalyticsEvent) {
        analyticsScope.launch {
            Firebase.analytics.logEvent(event.type) {
                for (extra in event.extras) {
                    param(
                        key = extra.key.take(40),
                        value = extra.value.take(100),
                    )
                }
            }
        }
    }

    override fun logError(
        throwable: Throwable,
        message: String?,
    ) {
        analyticsScope.launch {
            message?.let {
                Firebase.crashlytics.log("Error: $message")
            }
            Firebase.crashlytics.recordException(throwable)
        }
    }
}
