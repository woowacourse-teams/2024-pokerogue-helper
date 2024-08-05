package poke.rogue.helper.analytics

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

internal object AlphaAnalyticsLogger : AnalyticsLogger {
    override fun logEvent(event: AnalyticsEvent) {
        Timber.d("Event: $event")
    }

    override fun logError(throwable: Throwable, message: String?) {
        Timber.e(throwable, message)
        message ?: Firebase.crashlytics.log("Error: $message")
        Firebase.crashlytics.recordException(throwable)
    }
}