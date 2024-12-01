package poke.rogue.helper.testing.view

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher

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
