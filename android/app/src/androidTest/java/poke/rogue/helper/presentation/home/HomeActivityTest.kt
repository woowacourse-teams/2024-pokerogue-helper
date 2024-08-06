package poke.rogue.helper.presentation.home

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import poke.rogue.helper.R

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun 앱이_실행되면_포켓로그_로고가_보인다() {
        // then
        onView(withId(R.id.iv_home_logo))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 화면_회전_시에도_포켓로그_로고가_보인다() {
        // when
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // then
        onView(withId(R.id.iv_home_land_logo))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 화면_회전_시에도_타입_메뉴_버튼이_보인다() {
        // when
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // then
        onView(withId(R.id.cv_home_land_type))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 화면_회전_시에도_꿀팁_메뉴_버튼이_보인다() {
        // when
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // then
        onView(withId(R.id.cv_home_land_tip))
            .check(matches(isDisplayed()))
    }
}
