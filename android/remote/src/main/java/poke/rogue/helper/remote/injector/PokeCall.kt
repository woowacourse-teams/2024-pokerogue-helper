package poke.rogue.helper.remote.injector

import okhttp3.Request
import okio.Timeout
import poke.rogue.helper.remote.dto.base.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PokeCall<T : Any>(private val call: Call<T>) : Call<ApiResponse<T>> {

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = response.toApiResponse()
                return callback.onResponse(
                    this@PokeCall,
                    Response.success(apiResponse)
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val errorResponse = t.toErrorResponse()
                return callback.onResponse(
                    this@PokeCall,
                    Response.success(errorResponse)
                )
            }
        }
        )
    }

    private fun Response<T>.toApiResponse(): ApiResponse<T> {
        val code: Int = code()
        val error: String? = errorBody()?.string()
        return if (isSuccessful) { // 200..300
            val body = body()
            if (body != null) ApiResponse.Success(body)
            else ApiResponse.Failure.UnknownError(IllegalStateException("body == null"))
        } else { // 300..400
            ApiResponse.Failure.HttpException(code, IOException(error))
        }
    }

    private fun Throwable.toErrorResponse(): ApiResponse<T> = when (this) {
        // Network Error - ex) UnknownHostException, SocketTimeoutException, ConnectException
        is IOException -> ApiResponse.Failure.NetworkException(this)
        // 통신 이슈 외 - ex) JsonSyntaxException, IllegalStateException
        else -> ApiResponse.Failure.UnknownError(this)
    }


    override fun clone(): Call<ApiResponse<T>> = PokeCall(call.clone())

    override fun execute(): Response<ApiResponse<T>> {
        throw UnsupportedOperationException("PokeCall 은 execute() 를 지원하지 않습니다. enqueue() 를 사용해주세요.")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}