package poke.rogue.helper.util

import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.contrib.RecyclerViewActions
import org.hamcrest.CoreMatchers

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
