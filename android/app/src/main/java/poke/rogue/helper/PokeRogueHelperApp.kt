package poke.rogue.helper

import android.app.Application
import poke.rogue.helper.analytics.AnalyticsInitializer
import poke.rogue.helper.data.repository.DefaultDexRepository
import timber.log.Timber

class PokeRogueHelperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        AnalyticsInitializer.init()
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
}
