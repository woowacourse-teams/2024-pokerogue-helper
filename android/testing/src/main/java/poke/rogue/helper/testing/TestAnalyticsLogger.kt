package poke.rogue.helper.testing

import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.analytics.AnalyticsLogger

class TestAnalyticsLogger : AnalyticsLogger {
    private val events = mutableListOf<AnalyticsEvent>()
    private val errors = mutableListOf<Throwable>()

    override fun logEvent(event: AnalyticsEvent) {
        events.add(event)
    }

    override fun logError(
        throwable: Throwable,
        message: String?,
    ) {
        errors.add(throwable)
    }

    fun hasLogged(event: AnalyticsEvent) = event in events

    fun hasLoggedError(throwable: Throwable) = throwable in errors
}
