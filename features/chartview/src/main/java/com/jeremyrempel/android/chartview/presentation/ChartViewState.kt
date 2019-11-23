package com.jeremyrempel.android.chartview.presentation

import com.github.mikephil.charting.data.Entry

data class ChartViewState(
    val description: String,
    val data: List<Entry>
)
