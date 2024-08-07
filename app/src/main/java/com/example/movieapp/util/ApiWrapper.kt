package com.example.movieapp.util

import com.example.movieapp.presentation.state.ResultStates

suspend fun <T> apiWrapper(call: suspend () -> T): ResultStates<T> {
    return try {
        val response = call()
        //Log.d("success response", response.toString())
        ResultStates.Success(response)
    } catch (ex: Exception) {
        //Log.e("error response", ex.message.toString())
        ResultStates.Error(ex)
    }
}