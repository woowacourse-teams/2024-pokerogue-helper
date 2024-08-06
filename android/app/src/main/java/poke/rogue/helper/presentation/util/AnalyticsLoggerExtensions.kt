package poke.rogue.helper.presentation.util

import androidx.databinding.ViewDataBinding
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

fun <T : ViewDataBinding> AnalyticsLogger.logScreenView(clz: Class<T>) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras =
                listOf(
                    AnalyticsEvent.Param(
                        AnalyticsEvent.ParamKeys.SCREEN_NAME,
                        clz.componentType.name,
                    ),
                ),
        ),
    )
}
