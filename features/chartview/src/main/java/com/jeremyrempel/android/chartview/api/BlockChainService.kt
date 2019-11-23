package com.jeremyrempel.android.chartview.api

import io.reactivex.Single
import retrofit2.http.GET

interface BlockChainService {
    @GET("charts/market-price")
    fun marketPrice(): Single<BlockChainApiResponse>
}
