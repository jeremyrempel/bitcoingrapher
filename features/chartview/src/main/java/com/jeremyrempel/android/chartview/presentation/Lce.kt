package com.jeremyrempel.android.chartview.presentation

sealed class Lce<T> {
    class Loading<T> : Lce<T>()
    data class Content<T>(val result: T) : Lce<T>()
    data class Error<T>(val errorMsg: String) : Lce<T>()
}
