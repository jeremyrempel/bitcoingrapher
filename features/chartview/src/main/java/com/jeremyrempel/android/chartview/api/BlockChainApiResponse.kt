package com.jeremyrempel.android.chartview.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockChainApiResponse(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<Value>
) {
    @JsonClass(generateAdapter = true)
    data class Value(
        val x: Long, // unix timestamp
        val y: Double
    )
}
