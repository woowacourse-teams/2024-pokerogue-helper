package poke.rogue.helper.analytics

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

/**
 *  비동기적으로 데이터 수집, 분석, 로깅 등의 작업을 처리하는 용도로 사용하는 CoroutineScope
 *
 *  Firebase logEvent 는 내부 Executor 에서 동작하기 때문에 코루틴을 활용할 필요는 없지만
 *  해당 코드를 호출하는 프로덕션 코드에서 해당 함수에서 에러가 터지면 예외가 전파되어
 *  프로덕션 코드에 문제를 일으킬 수 있기 때문에 별도의 코루틴을 사용하여 처리한다.
 *
 *  ref: https://velog.io/@murjune/kotlin-Coroutine-supervisorScope-vs-SupervisorJob-%EC%96%B4%EB%96%A4%EA%B1%B8-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC%EB%8A%94%EA%B1%B0%EC%A7%80
 */
private val analyticsExcpetionHandler =
    CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
    }
internal val analyticsScope =
    CoroutineScope(SupervisorJob() + Dispatchers.IO + analyticsExcpetionHandler)
