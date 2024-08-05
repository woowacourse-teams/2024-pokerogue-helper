package poke.rogue.helper.presentation.util

import androidx.fragment.app.Fragment
import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.analytics.AnalyticsLogger

fun AnalyticsLogger.logClickEvent(eventName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.ACTION,
            extras =
                listOf(
                    AnalyticsEvent.Param(AnalyticsEvent.ParamKeys.ACTION_NAME, eventName),
                ),
        ),
    )
}

inline fun <reified T : Fragment> AnalyticsLogger.logScreenView() {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras =
                listOf(
                    AnalyticsEvent.Param(
                        AnalyticsEvent.ParamKeys.SCREEN_NAME,
                        T::class.java.simpleName,
                    ),
                ),
        ),
    )
}
