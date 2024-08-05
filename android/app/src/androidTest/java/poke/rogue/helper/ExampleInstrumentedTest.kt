package poke.rogue.helper

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import poke.rogue.helper.presentation.util.testContext

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = testContext
        assertEquals("poke.rogue.helper", appContext.packageName)
    }
}
