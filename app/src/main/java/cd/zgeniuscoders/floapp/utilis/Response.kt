package cd.zgeniuscoders.floapp.utilis

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Response<T>(data)
    class Error<T>(message: String, data: T? = null) : Response<T>(data, message)
}