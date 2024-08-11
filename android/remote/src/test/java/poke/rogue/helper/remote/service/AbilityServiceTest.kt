package poke.rogue.helper.remote.service

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.injector.RetrofitModule
import poke.rogue.helper.remote.service.utils.getOrThrow
import poke.rogue.helper.remote.service.utils.httpErrorResponse
import poke.rogue.helper.remote.service.utils.shouldBeHttpException
import poke.rogue.helper.remote.service.utils.shouldBeNetworkException
import poke.rogue.helper.remote.service.utils.shouldBeSuccess
import poke.rogue.helper.remote.service.utils.shouldBeUnknownError
import poke.rogue.helper.remote.service.utils.successResponse
import retrofit2.create
import java.io.IOException
import java.net.ConnectException

class AbilityServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: AbilityService2

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        service = RetrofitModule.testRetrofit(mockWebServer.url("")).create()
    }

    @Test
    fun `포켓몬의 모든 특성들을 가져온다`() =
        runTest {
            // given
            val fakeResponse = successResponse("abilities")
            mockWebServer.enqueue(fakeResponse)
            // when
            val actual: ApiResponse<List<AbilityResponse>> = service.abilities()
            // then
            actual.shouldBeSuccess()
        }

    @Test
    fun `id 에 해당하는 특성을 가져온다`() =
        runTest {
            // given
            val fakeResponse = successResponse("ability")
            mockWebServer.enqueue(fakeResponse)
            // when
            val actual: ApiResponse<AbilityDetailResponse> = service.ability()
            // then
            actual.shouldBeSuccess()
        }

    @Test
    fun `HttpException 발생`() =
        runTest {
            // given
            val fakeResponse = httpErrorResponse(404)
            mockWebServer.enqueue(fakeResponse)
            // when
            val actual: ApiResponse<List<AbilityResponse>> = service.abilities()
            // then
            assertSoftly {
                actual.shouldBeHttpException()
                shouldThrow<IOException> { actual.getOrThrow() }
            }
        }

    @Test
    fun `NetworkException - ConnectException 발생`() =
        runTest {
            // given
            val fakeResponse = MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
            mockWebServer.enqueue(fakeResponse)
            // when
            val actual: ApiResponse<List<AbilityResponse>> = service.abilities()
            // then
            assertSoftly {
                actual.shouldBeNetworkException()
                shouldThrow<ConnectException> { actual.getOrThrow() }
            }
        }

    @Test
    fun `UnKnownException - 직렬화 예외 발생`() =
        runTest {
            // given
            val fakeResponse = MockResponse()
            mockWebServer.enqueue(fakeResponse)
            // when
            val actual: ApiResponse<List<AbilityResponse>> = service.abilities()
            println(actual)
            // then
            assertSoftly {
                actual.shouldBeUnknownError()
                shouldThrow<SerializationException> { actual.getOrThrow() }
            }
        }
}
