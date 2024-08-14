package poke.rogue.helper.presentation.util.event

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class EventFlowTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `EventFlow 는 소비할 때까지 element 가 삭제되지 않는다`() = runTest(UnconfinedTestDispatcher()) {
        // given
        val eventFlow = MutableEventFlow<Int>()
        // when
        eventFlow.emit(1)
        delay(10)
        // then
        eventFlow
            .onEach {
                println(">>> onEach: $it")
                it shouldBe 1
            }
            .launchIn(backgroundScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `EventFlow 는 element 를 공유하지 않는다`() = runTest {
        // given
        val eventFlow = MutableEventFlow<Int>()
        // when
        eventFlow.emit(1)
        delay(10)
        // then
        backgroundScope.launch {
            launch {
                eventFlow.collect {
                    println(">>> collect: $it")
                    it shouldBe 1
                }
            }
            launch {
                eventFlow.collect {
                    println(">>> Never Collect AnyThing")
                    it shouldBe Int.MAX_VALUE
                }
            }
        }
        advanceTimeBy(100)
    }
}