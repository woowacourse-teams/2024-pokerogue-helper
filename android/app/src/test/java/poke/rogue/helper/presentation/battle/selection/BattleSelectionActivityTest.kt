package poke.rogue.helper.presentation.battle.selection

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.viewpager2.widget.ViewPager2
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.robolectric.annotation.Config
import poke.rogue.helper.R
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SelectionMode
import poke.rogue.helper.presentation.battle.selection.pokemon.PokemonSelectionFragment
import poke.rogue.helper.presentation.battle.selection.pokemon.PokemonSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.skill.SkillSelectionFragment
import poke.rogue.helper.presentation.battle.selection.skill.SkillSelectionViewModel
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.TestApplication
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class BattleSelectionActivityTest {
    private val intent =
        BattleSelectionActivity.intent(
            ApplicationProvider.getApplicationContext(),
            SelectionMode.POKEMON_AND_SKILL,
            SelectionData.NoSelection,
        )

    @get:Rule
    val activityRule = activityScenarioRule<BattleSelectionActivity>(intent)
    val scenario get() = activityRule.scenario

    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Test
    fun `Activity 실행 테스트`() {
        scenario.onActivity { activity ->
            activity.shouldNotBeNull()
        }
    }

    @Test
    fun `ViewModel 주입 테스트`() {
        scenario.onActivity { activity ->
            activity.getViewModel<BattleSelectionViewModel>().shouldNotBeNull()
        }
    }

    @Test
    fun `포켓몬 선택 Fragment 테스트`() {
        scenario.onActivity { activity ->
            val pokemonSelectionFragment =
                activity.supportFragmentManager.fragments.find { it is PokemonSelectionFragment }
            pokemonSelectionFragment.shouldNotBeNull()

            val pokemonSelectionViewModel =
                pokemonSelectionFragment.getViewModel<PokemonSelectionViewModel>()
            pokemonSelectionViewModel.shouldNotBeNull()
        }
    }

    @Test
    fun `스킬 선택 Fragment 테스트`() {
        scenario.onActivity { activity ->
            val viewPager = activity.findViewById<ViewPager2>(R.id.pager_battle_selection)
            viewPager.currentItem = SelectionStep.SKILL_SELECTION.ordinal

            viewPager.post {
                val skillSelectionFragment =
                    activity.supportFragmentManager.fragments.find { it is SkillSelectionFragment }
                skillSelectionFragment.shouldNotBeNull()

                val skillSelectionViewModel = activity.getViewModel<SkillSelectionViewModel>()
                skillSelectionViewModel.shouldNotBeNull()
            }
        }
    }
}
