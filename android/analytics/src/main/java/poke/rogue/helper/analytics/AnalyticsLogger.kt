package poke.rogue.helper.analytics

private const val DEBUG_MODE = "debug"
private const val ALPHA_MODE = "alpha"
private const val BETA_MODE = "beta"
private const val RELEASE_MODE = "release"

/** Analytics API surface */
interface AnalyticsLogger {
    fun logEvent(event: AnalyticsEvent)

    fun logError(
        throwable: Throwable,
        message: String? = null,
    )
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
