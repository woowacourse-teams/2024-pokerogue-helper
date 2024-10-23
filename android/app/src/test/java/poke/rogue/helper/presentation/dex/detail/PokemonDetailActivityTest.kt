package poke.rogue.helper.presentation.dex.detail

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.robolectric.annotation.Config
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.TestApplication
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class PokemonDetailActivityTest {
    @get:Rule
    val activityRule = activityScenarioRule<PokemonDetailActivity>()
    private val scenario get() = activityRule.scenario

    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Test
    fun `액티비티가 정상적으로 실행된다`() {
        scenario.onActivity { activity ->
            activity.shouldNotBeNull()
        }
    }

    @Test
    fun `액티비티가 구성 변경되어도 같은 뷰모델 인스턴스 사용`() {
        // given
        var previewVm: PokemonDetailViewModel? = null
        scenario.onActivity { activity ->
            previewVm = activity.getViewModel()
        }

        // when
        scenario.recreate()

        // then
        scenario.onActivity {
            it.getViewModel<PokemonDetailViewModel>() shouldBe previewVm
        }
    }
}
