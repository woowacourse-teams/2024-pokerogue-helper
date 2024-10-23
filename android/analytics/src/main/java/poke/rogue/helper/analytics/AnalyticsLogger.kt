package poke.rogue.helper.analytics

internal const val DEBUG_MODE = "debug"
internal const val ALPHA_MODE = "alpha"
internal const val BETA_MODE = "beta"
internal const val RELEASE_MODE = "release"

/** Analytics API surface */
interface AnalyticsLogger {
    fun logEvent(event: AnalyticsEvent)

    fun logError(
        throwable: Throwable,
        message: String? = null,
    )

    companion object {
        val Stub =
            object : AnalyticsLogger {
                override fun logEvent(event: AnalyticsEvent) = Unit

                override fun logError(
                    throwable: Throwable,
                    message: String?,
                ) = Unit
            }
    }
}

fun analyticsLogger(): AnalyticsLogger {
    return when (BuildConfig.BUILD_TYPE) {
        DEBUG_MODE -> DebugAnalyticsLogger
        ALPHA_MODE -> AlphaAnalyticsLogger
        BETA_MODE -> FireBaseAnalyticsLogger
        RELEASE_MODE -> FireBaseAnalyticsLogger
        else -> error("Unknown build type")
    }
}
