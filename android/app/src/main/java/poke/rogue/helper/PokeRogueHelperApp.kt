package poke.rogue.helper

import android.app.Application
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import poke.rogue.helper.data.repository.DefaultDexRepository
import timber.log.Timber

class PokeRogueHelperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initFirebase()
        DefaultDexRepository.init(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(
                object : Timber.DebugTree() {
                    override fun createStackElementTag(element: StackTraceElement): String {
                        return "${element.fileName} : ${element.lineNumber} - ${element.methodName}"
                    }
                },
            )
        }
    }

    private fun initFirebase() {
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

    companion object {
        private const val DEBUG_MODE = "debug"
        private const val ALPHA_MODE = "alpha"
        private const val BETA_MODE = "beta"
        private const val RELEASE_MODE = "release"
    }
}
