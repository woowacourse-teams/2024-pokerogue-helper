package poke.rogue.helper.remote.interceptor

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.util.Locale

class LocaleInterceptorTest : BehaviorSpec({

    val mockWebServer = MockWebServer()

    beforeSpec {
        mockWebServer.start()
    }

    afterSpec {
        mockWebServer.shutdown()
    }

    Given("디바이스가 언어는 한국어로, 지역은 한국으로 설정되어 있다.") {
        val locale = Locale("ko", "KR")
        Locale.setDefault(locale)

        val client =
            OkHttpClient.Builder()
                .addInterceptor(LocaleInterceptor())
                .build()

        val request =
            Request.Builder()
                .url(mockWebServer.url("/test"))
                .build()

        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        When("요청이 불릴 때") {
            client.newCall(request).execute()

            Then("헤더에 들어가는 Accept-Language 는 ko-KR 이다.") {
                val recordedRequest = mockWebServer.takeRequest()
                val headerValue = recordedRequest.getHeader("Accept-Language")
                headerValue shouldBe "ko-KR"
            }
        }
    }
})
