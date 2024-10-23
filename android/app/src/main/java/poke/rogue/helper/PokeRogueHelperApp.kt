package poke.rogue.helper

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import poke.rogue.helper.di.appModule
import timber.log.Timber

class PokeRogueHelperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            modules(appModule)
        }
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
