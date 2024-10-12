package poke.rogue.helper.analytics.di

import org.koin.dsl.module
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger

val analyticsModule = module {
    single<AnalyticsLogger> { analyticsLogger() }
}