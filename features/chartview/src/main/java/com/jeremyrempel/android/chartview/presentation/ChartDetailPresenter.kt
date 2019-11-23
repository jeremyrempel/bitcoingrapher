package com.jeremyrempel.android.chartview.presentation

import io.reactivex.Observable

interface ChartDetailPresenter {
    fun getViewState(): Observable<Lce<ChartViewState>>
    fun requestData()
    fun clear()
}
