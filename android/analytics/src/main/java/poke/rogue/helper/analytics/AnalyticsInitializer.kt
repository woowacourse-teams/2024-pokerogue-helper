package poke.rogue.helper.analytics

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

object AnalyticsInitializer {
    fun init() {
        when (BuildConfig.BUILD_TYPE) {
            DEBUG_MODE -> {
                Firebase.analytics.setAnalyticsCollectionEnabled(false)
                Firebase.crashlytics.setCrashlyticsCollectionEnabled(false)
            }

            ALPHA_MODE -> {
                Firebase.analytics.setAnalyticsCollectionEnabled(false)
                Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
            }

            BETA_MODE, RELEASE_MODE -> {
                Firebase.analytics.setAnalyticsCollectionEnabled(true)
                Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
            }

            else -> error("Unknown build type")
        }
    }
}
