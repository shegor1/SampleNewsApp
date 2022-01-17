package com.shegor.samplenewsapp.base.base


import android.util.Log
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

abstract class BaseRepository {

    suspend fun <T : Any> apiCall(call : suspend() -> Response<T>, error: String) : T?{
        val result = apiOutput(call, error)

        var output : T? = null
        when (result){
            is NetworkOutput.Success -> output = result.output
            is NetworkOutput.Error -> Log.e("Error", "The $error and the ${result.exception}")
        }
        return output
    }

    private suspend fun <T: Any> apiOutput(call: suspend () -> Response<T>, error: String): NetworkOutput<T> {
        val response = call()
        return if (response.isSuccessful)
            NetworkOutput.Success(response.body()!!)
        else
            NetworkOutput.Error(IOException("Something went wrong due to $error"))
    }

    sealed class NetworkOutput<out T: Any>{
        data class Success<out T : Any>(val output: T) : NetworkOutput<T>()
        data class Error(val exception: Exception) : NetworkOutput<Nothing>()
    }
}