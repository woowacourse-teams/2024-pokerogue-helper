package poke.rogue.helper

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.string.shouldContain
import org.junit.Test
import org.junit.runner.RunWith
import poke.rogue.helper.testing.testContext

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = testContext
        appContext.packageName shouldContain "poke.rogue.helper"
    }
}
