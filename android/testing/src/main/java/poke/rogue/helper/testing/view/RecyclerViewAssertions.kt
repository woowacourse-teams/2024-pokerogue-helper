package poke.rogue.helper.testing.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.hamcrest.CoreMatchers

/**
 * 리사이클러뷰의 아이템 개수를 확인하는 ViewAssertion을 반환하는 함수
 *
 * sample
 * ```kotlin
 * // 리사이클러뷰의 아이템 개수가 3개인지 확인
 * onView(withId(R.id.rv_shopping_cart)).check(withItemCount(3))
 * ```
 */
fun withItemCount(expectedCount: Int): ViewAssertion {
    return RecyclerViewItemCountAssertion(expectedCount)
}

/**
 * 리사이클러뷰의 expectedCount만큼 아이템이 있는지 확인하는 ViewAssertion
 */
private class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(
        view: View?,
        noViewFoundException: NoMatchingViewException?,
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        require(view is RecyclerView) {
            "View is not RecyclerView"
        }
        val adapter = view.adapter
        adapter.shouldNotBeNull()
        adapter.itemCount shouldBe expectedCount
    }
}

/**
 * 자식 뷰의 텍스트를 포함하는 뷰가 있는지 확인하는 ViewAssertion을 반환하는 함수
 *
 * sample
 * ```kotlin
 * // 리사이클러뷰의 자식 뷰 중 텍스트에 "텍스트"가 포함된 뷰가 있는지 확인
 * onView(withId(R.id.rv_shopping_cart)).check(matchDescendantWithText("텍스트"))
 * ```
 */
fun matchDescendantWithText(text: String): ViewAssertion {
    return ViewAssertions.matches(
        ViewMatchers.hasDescendant(
            ViewMatchers.withText(
                CoreMatchers.containsString(text),
            ),
        ),
    )
}
