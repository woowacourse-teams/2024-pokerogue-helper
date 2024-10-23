package poke.rogue.helper.presentation.dex.detail.information

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.robolectric.annotation.Config
import poke.rogue.helper.R
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.TestApplication
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class PokemonInformationFragmentTest {
    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Test
    fun `프래그먼트가 정상적으로 실행된다`() {
        val scenario =
            launchFragmentInContainer<PokemonInformationFragment>(
                themeResId = R.style.Theme_PokeRogueHelper,
            )

        scenario.onFragment { fragment ->
            fragment.shouldNotBeNull()
        }
    }

    @Test
    fun `프래그먼트 구성 변경 시에도 같은 뷰모델 인스턴스`() {
        // given
        val scenario =
            launchFragmentInContainer<PokemonInformationFragment>(
                themeResId = R.style.Theme_PokeRogueHelper,
            )

        var previewViewModel: PokemonDetailViewModel? = null
        scenario.onFragment { fragment ->
            previewViewModel = fragment.getViewModel<PokemonDetailViewModel>()
        }

        // when
        scenario.recreate()

        // then
        scenario.onFragment { fragment ->
            fragment.getViewModel<PokemonDetailViewModel>() shouldBe previewViewModel
        }
    }
}
