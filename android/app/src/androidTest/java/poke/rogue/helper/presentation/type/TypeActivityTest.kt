package poke.rogue.helper.presentation.type

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import poke.rogue.helper.R
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.rule.KoinAndroidUnitTestRule

@RunWith(AndroidJUnit4::class)
class TypeActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(TypeActivity::class.java)

    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testViewModelModule,
        )

    @Test
    @DisplayName("사용자가 아무것도 선택하지 않은 경우에는 선택 안내 이미지가 보인다")
    fun test1() {
        // then
        onView(ViewMatchers.withId(R.id.iv_no_selection))
            .check(matches(isDisplayed()))
    }

    @Test
    @DisplayName("화면 회전시에도 사용자가 아무것도 선택하지 않은 경우에는 선택 안내 이미지가 보인다")
    fun test2() {
        // when
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onIdle()

        // then
        onView(ViewMatchers.withId(R.id.iv_no_selection))
            .check(matches(isDisplayed()))
    }
}
