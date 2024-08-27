package com.example.movieapp.util

import android.util.Log
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> apiWrapper(call: suspend () -> T): ResultStates<T> {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("CoroutineException", "Caught $exception")
    }

    return withContext(Dispatchers.IO + exceptionHandler) {
        try {
            val response = call()
            //Log.d("success response", response.toString())
            ResultStates.Success(response)
        } catch (ex: Exception) {
            //Log.e("error response", ex.message.toString())
            ResultStates.Error(ex)
        }
    }
}