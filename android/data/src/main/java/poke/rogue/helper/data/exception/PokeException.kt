package poke.rogue.helper.data.exception

sealed class PokeException(val error: Throwable) : RuntimeException(error)

class HttpException(code: Int, throwable: Throwable) : PokeException(throwable) {
    init {
        require(code in ERROR_CODE_RANGE)
    }

    companion object {
        private val ERROR_CODE_RANGE = 100..599
    }
}

class NetworkException(throwable: Throwable) : PokeException(throwable)

class UnknownException(throwable: Throwable) : PokeException(throwable)
