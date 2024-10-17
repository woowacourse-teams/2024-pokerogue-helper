package poke.rogue.helper.analytics.di

import org.koin.dsl.module
import poke.rogue.helper.analytics.AnalyticsInitializer
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger

val analyticsModule
    get() =
        module {
            AnalyticsInitializer.init()
            single<AnalyticsLogger> { analyticsLogger() }
        }
