package poke.rogue.helper.presentation.home

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import poke.rogue.helper.R

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    @DisplayName("앱이 실행되면 포켓로그 로고가 보인다")
    fun test1() {
        // then
        onView(withId(R.id.iv_home_logo))
            .check(matches(isDisplayed()))
    }

    @Test
    @DisplayName("화면 회전 시에도 포켓로그 로고가 보인다")
    fun test2() {
        // when
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onIdle()

        // then
        onView(withId(R.id.iv_home_land_logo))
            .check(matches(isDisplayed()))
    }

    @Test
    @DisplayName("화면 회전 시에도 타입 메뉴 버튼이 보인다")
    fun test3() {
        // when
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onIdle()

        // then
        onView(withId(R.id.cv_home_land_type))
            .check(matches(isDisplayed()))
    }
}
