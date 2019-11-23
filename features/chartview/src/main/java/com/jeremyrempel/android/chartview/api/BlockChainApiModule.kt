package com.jeremyrempel.android.chartview.api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object BlockChainApiModule {

    @Provides
    @Singleton
    fun providesBlockChainService(retrofit: Retrofit): BlockChainService {
        return retrofit.create(BlockChainService::class.java)
    }
}
