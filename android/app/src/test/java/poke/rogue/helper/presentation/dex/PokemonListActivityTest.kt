package poke.rogue.helper.presentation.dex

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.nulls.shouldNotBeNull
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper
import poke.rogue.helper.R
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.TestApplication
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule
import poke.rogue.helper.testing.view.withItemCount

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class PokemonListActivityTest {
    @get:Rule
    val activityRule = activityScenarioRule<PokemonListActivity>()
    val scenario get() = activityRule.scenario

    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Before
    fun setUp() {
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
    }

    @Test
    fun `Activity 실행 테스트`() {
        scenario.onActivity { activity ->
            activity.shouldNotBeNull()
        }
    }

    @Test
    fun `포켓몬 리스트가 보인다`() {
        onView(withId(R.id.rv_pokemon_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `전체 포켓몬 리스트는 10개다`() {
        onView(withId(R.id.rv_pokemon_list))
            .check(withItemCount(10))
    }

    @Test
    fun `스크롤 down 시 헤더 그룹이 사라지고 스크롤 up시 헤더 그룹이 보인다`() {
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
