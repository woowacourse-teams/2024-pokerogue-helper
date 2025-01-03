package poke.rogue.helper.presentation.util

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.contrib.RecyclerViewActions
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

/**
 * 리사이클러뷰의 expectedCount만큼 아이템이 있는지 확인하는 ViewAssertion
 */
class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(
        view: View?,
        noViewFoundException: NoMatchingViewException?,
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as? RecyclerView
        recyclerView.shouldNotBeNull()
        val adapter = recyclerView.adapter
        adapter.shouldNotBeNull()
        adapter.itemCount shouldBe expectedCount
    }
}

/**
 * 리사이클러뷰의 특정 위치에 있는 아이템을 클릭하는 ViewAction
 *
 * reference: https://gist.github.com/quentin7b/9c5669fd940865cf2e89
 *
 * sample
 * ```kotlin
 * onView(withId(R.id.rv_shopping_cart)).perform(
 *         RecyclerViewActions.actionOnItemAtPosition<CartAdapter.CartViewHolder>(
 *             3,  // 3번째 아이템
 *             clickChildViewWithId(R.id.iv_shooping_cart_delete), // id가 iv_shooping_cart_delete인 뷰 클릭
 *         ),
 *     )

 * ```
 */
fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "View id: $id - Click on specific button"
        }

        override fun perform(
            uiController: UiController,
            view: View,
        ) {
            val v = view.findViewById<View>(id)
            v.performClick()
        }
    }
}

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

inline fun <reified T : RecyclerView.ViewHolder> ViewInteraction.performScrollToHolder(position: Int = 0): ViewInteraction {
    return perform(
        RecyclerViewActions.scrollToHolder(CoreMatchers.instanceOf(T::class.java))
            .atPosition(position),
    )
}

fun <T : RecyclerView.ViewHolder> ViewInteraction.performClickHolderAt(
    absolutePosition: Int = 0,
    @IdRes childViewId: Int,
): ViewInteraction {
    return perform(
        RecyclerViewActions.actionOnItemAtPosition<T>(
            absolutePosition,
            clickChildViewWithId(childViewId),
        ),
    )
}
