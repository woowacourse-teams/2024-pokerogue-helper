package poke.rogue.helper.remote.injector

import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.VisibleForTesting
import poke.rogue.helper.remote.BuildConfig
import poke.rogue.helper.remote.interceptor.RedirectInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitModule {
    private const val LOCAL_HOST_BASE_URL = "http://10.0.2.2:8080"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.POKE_BASE_URL)
            .client(okHttpClient(loggingInterceptor()))
            .addCallAdapterFactory(PokeCallAdapterFactory())
            .addConverterFactory(PokeConverterFactory(json(), jsonConverterFactory(json())))
            .build()
    }

    private fun json(): Json =
        if (BuildConfig.DEBUG) {
            Json {
                coerceInputValues = true
            }
        } else {
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }

    private fun jsonConverterFactory(json: Json): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    private fun loggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )

    private fun okHttpClient(logInterceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(logInterceptor)
            .let { client ->
                if (BuildConfig.DEBUG) {
                    client.addInterceptor(RedirectInterceptor(LOCAL_HOST_BASE_URL))
                } else {
                    client
                }
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    fun retrofit(): Retrofit = retrofit

    @VisibleForTesting
    fun testRetrofit(path: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .baseUrl(path)
            .addCallAdapterFactory(PokeCallAdapterFactory())
            .addConverterFactory(PokeConverterFactory(json(), jsonConverterFactory(json())))
            .build()
    }
}
