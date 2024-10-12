package poke.rogue.helper.remote.service.di

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import poke.rogue.helper.remote.di.retrofitModule
import poke.rogue.helper.remote.injector.PokeCallAdapterFactory
import poke.rogue.helper.remote.injector.PokeConverterFactory
import poke.rogue.helper.remote.service.AbilityService
import retrofit2.Retrofit
import retrofit2.create

val testRemoteModule = module {
    includes(retrofitModule)

    single<OkHttpClient>(named("test")) {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit>(named("test")) { (path: HttpUrl) ->
        Retrofit.Builder()
            .client(get<OkHttpClient>(named("test")))
            .baseUrl(path)
            .addCallAdapterFactory(get<PokeCallAdapterFactory>())
            .addConverterFactory(get<PokeConverterFactory>())
            .build()
    }

    single<AbilityService> { (path: HttpUrl) ->
        get<Retrofit>(named("test")) { parametersOf(path) }.create()
    }
}