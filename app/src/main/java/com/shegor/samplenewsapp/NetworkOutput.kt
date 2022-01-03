package com.shegor.samplenewsapp

import java.lang.Exception

sealed class NetworkOutput<out T: Any>{
    data class Success<out T : Any>(val output: T) : NetworkOutput<T>()
    data class Error(val exception: Exception) : NetworkOutput<Nothing>()
    //data class Empty() : Output<Nothing>()
}
