package poke.rogue.helper.presentation.dex

import androidx.lifecycle.lifecycleScope
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.nulls.shouldNotBeNull
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.android.getViewModel
import poke.rogue.helper.R
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.idleresource.asIdlingResource
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule
import poke.rogue.helper.testing.view.withItemCount

@RunWith(AndroidJUnit4::class)
class PokemonListActivityTest {
    @get:Rule
    val activityRule = activityScenarioRule<PokemonListActivity>()
    val scenario get() = activityRule.scenario
    private lateinit var idlingResource: IdlingResource

    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Before
    fun setUp() {
        scenario.onActivity { activity ->
            val viewModel = activity.getViewModel<PokemonListViewModel>()

            // StateFlow의 값이 비어 있지 않은 상태를 Idle로 간주
            idlingResource =
                viewModel.uiState.asIdlingResource(activity.lifecycleScope) { uiState ->
                    uiState.pokemons.isNotEmpty()
                }

            // IdlingResource 등록
            IdlingRegistry.getInstance().register(idlingResource)
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun `Activity_실행_테스트`() {
        scenario.onActivity { activity ->
            activity.shouldNotBeNull()
        }
    }

    @Test
    fun `포켓몬_리스트가_화면에_보인다`() {
        onView(withId(R.id.rv_pokemon_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `포켓몬_리스트_아이템_카운트_테스트`() {
        onView(withId(R.id.rv_pokemon_list))
            .check(withItemCount(10))
    }

    @Test
    fun `스크롤_down_시_헤더_그룹이_사라지고_스크롤_up_시_헤더_그룹이_보인다`() {
        // scroll down
        onView(withId(R.id.root_pokemon_list))
            .perform(ViewActions.swipeUp())
        onView(withId(R.id.header_group))
            .check(matches(not(isDisplayed())))
        // scroll up
        onView(withId(R.id.root_pokemon_list))
            .perform(ViewActions.swipeDown())
        onView(withId(R.id.header_group))
            .check(matches(isDisplayed()))
    }
}
