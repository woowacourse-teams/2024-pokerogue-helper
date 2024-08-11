package poke.rogue.helper.remote.injector

import poke.rogue.helper.remote.dto.base.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class PokeCallAdapterFactory : CallAdapter.Factory() {
    // getRawType - 기저 타입
    // List<String>과 같은 ParameterizedType이 있는 경우 getRawType은 List.class를 반환합니다.

    // getParameterUpperBound - 제네릭 타입의 인자를 반환합니다.
    // List<String> -> getParameterUpperBound(0, returnType) -> String.class
    // Map<String, Int> -> getParameterUpperBound(1, returnType) -> Int.class
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // 1) getRawType - Call<ApiResponse<Foo>>
        val rawType: Type = getRawType(returnType)
        if (rawType != Call::class.java) return null
        require(returnType is ParameterizedType) { //2
            "returnType(=${returnType}) 은 반드시 Call<ApiResponse<Foo>> 형태여야 함"
        }

        // 2) responseType - ApiResponse<Foo>
        val responseType: Type = getParameterUpperBound(0, returnType)
        if(getRawType(responseType) != ApiResponse::class.java) return null
        require(responseType is ParameterizedType) {
            "responseType(=${responseType}) 은 반드시 ApiResponse<Foo> or ApiResponse<out Foo> 형태여야 함"
        }

        // 3) bodyType - Foo
        val bodyType = getParameterUpperBound(0, responseType)
        return PokeCallAdapter<Any>(bodyType)
    }

    private class PokeCallAdapter<T : Any>(private val responseType: Type) :
        CallAdapter<T, Call<ApiResponse<T>>> {
        override fun responseType(): Type = responseType

        override fun adapt(call: Call<T>): Call<ApiResponse<T>> = PokeCall<T>(call)
    }
}