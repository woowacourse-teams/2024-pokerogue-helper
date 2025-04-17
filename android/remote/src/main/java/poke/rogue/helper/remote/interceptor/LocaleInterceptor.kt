package poke.rogue.helper.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class LocaleInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val localeCode = Locale.getDefault().toLanguageTag()
        val request =
            chain.request().newBuilder()
                .addHeader("Accept-Language", localeCode)
                .build()

        return chain.proceed(request)
    }
}
