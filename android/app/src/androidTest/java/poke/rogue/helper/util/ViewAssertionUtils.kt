package poke.rogue.helper.util

import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import poke.rogue.helper.util.RecyclerViewItemCountAssertion

/**
 * 자식 뷰의 텍스트를 포함하는 뷰가 있는지 확인하는 ViewAssertion을 반환하는 함수
 *
 * sample
 * ```kotlin
 * // 리사이클러뷰의 자식 뷰 중 텍스트에 "텍스트"가 포함된 뷰가 있는지 확인
 * onView(withId(R.id.rv_shopping_cart)).check(matchDescendantSoftly("텍스트"))
 * ```
 */
fun matchDescendantSoftly(text: String): ViewAssertion {
    return ViewAssertions.matches(
        ViewMatchers.hasDescendant(
            ViewMatchers.withText(
                CoreMatchers.containsString(text),
            ),
        ),
    )
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
