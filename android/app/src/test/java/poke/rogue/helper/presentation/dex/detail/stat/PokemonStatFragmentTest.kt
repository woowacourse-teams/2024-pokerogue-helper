package poke.rogue.helper.presentation.dex.detail.stat

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import poke.rogue.helper.R
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.TestApplication
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class PokemonStatFragmentTest {
    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Test
    fun `프래그먼트가 정상적으로 실행된다`() {
        val scenario =
            launchFragmentInContainer<PokemonStatFragment>(
                themeResId = R.style.Theme_PokeRogueHelper,
            )

        scenario.onFragment { fragment ->
            fragment.shouldNotBeNull()
        }
    }
}
