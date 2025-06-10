package com.example.movieapp.util

import java.util.Locale

fun Double.voteAverageFormatted(): String {
    return String.format(Locale.US, "%.1f", this)
}