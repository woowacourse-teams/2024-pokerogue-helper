import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import poke.rogue.helper.R
import poke.rogue.helper.presentation.ability.detail.AbilityDetailFragment
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.TestApplication
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class AbilityDetailFragmentTest {
    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Ignore("Issue: launchFragmentInContainer")
    @Test
    fun `Fragment 실행 테스트`() {
        val scenario =
            launchFragmentInContainer<AbilityDetailFragment>(themeResId = R.style.Theme_PokeRogueHelper)

        scenario.onFragment { fragment ->
            fragment.shouldNotBeNull()
        }
    }
}
