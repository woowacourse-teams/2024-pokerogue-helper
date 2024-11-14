package poke.rogue.helper.analytics

import timber.log.Timber

internal object DebugAnalyticsLogger : AnalyticsLogger {
    override fun logEvent(event: AnalyticsEvent) {
        Timber.d("Event: $event")
    }

    override fun logError(
        throwable: Throwable,
        message: String?,
    ) {
        Timber.e(throwable, message)
    }
}
