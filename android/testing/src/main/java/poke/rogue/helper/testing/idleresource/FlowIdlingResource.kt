package poke.rogue.helper.testing.idleresource

import androidx.test.espresso.IdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FlowIdlingResource<T>(
    flow: Flow<T>,
    coroutineScope: CoroutineScope,
    private val idleCondition: (T) -> Boolean,
) : IdlingResource {
    @Volatile
    private var isIdle = false
    private var callback: IdlingResource.ResourceCallback? = null

    init {
        flow.onEach { value ->
            isIdle = idleCondition(value)
            if (isIdle) {
                callback?.onTransitionToIdle() // Idle 상태 알림
            }
        }.launchIn(coroutineScope)
    }

    override fun getName(): String = this::class.java.simpleName

    override fun isIdleNow(): Boolean = isIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}
