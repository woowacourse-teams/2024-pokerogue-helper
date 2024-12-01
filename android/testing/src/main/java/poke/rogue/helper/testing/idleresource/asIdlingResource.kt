package poke.rogue.helper.testing.idleresource

import androidx.test.espresso.IdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

inline fun <reified T : Any> Flow<T>.asIdlingResource(
    coroutineScope: CoroutineScope,
    crossinline idleCondition: (T) -> Boolean,
): IdlingResource {
    return FlowIdlingResource(this, coroutineScope) { value ->
        idleCondition(value)
    }
}
