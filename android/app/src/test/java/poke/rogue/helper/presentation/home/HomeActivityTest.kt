package poke.rogue.helper.presentation.home

import android.widget.TextView
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
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
class HomeActivityTest {
    @get:Rule
    val activityRule = activityScenarioRule<HomeActivity>()
    private val activityScenario get() = activityRule.scenario

    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Test
    fun `Activity 실행 테스트`() {
        activityScenario.onActivity { activity ->
            activity.shouldNotBeNull()
        }
    }

    @Test
    @Config(qualifiers = "ko-rKR")
    fun `한국어 설정일 때 한국어 문자열 리소스로 보인다`() {
        activityScenario.onActivity { activity ->
            val dexTextView = activity.findViewById<TextView>(R.id.tv_home_dex)
            dexTextView.text.toString() shouldBe "포켓몬 도감"
        }
    }

    @Test
    fun `기본 설정일 때 영어 문자열 리소스로 보인다 `() {
        activityScenario.onActivity { activity ->
            val dexTextView = activity.findViewById<TextView>(R.id.tv_home_dex)
            dexTextView.text.toString() shouldBe "Pokémon Dex"
        }
    }
}
