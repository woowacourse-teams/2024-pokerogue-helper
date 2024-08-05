package poke.rogue.helper.presentation.util

import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

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
