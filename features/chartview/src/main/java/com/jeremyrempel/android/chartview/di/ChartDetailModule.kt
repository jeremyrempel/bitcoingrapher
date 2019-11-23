package com.jeremyrempel.android.chartview.di

import com.jeremyrempel.android.chartview.api.BlockChainApiModule
import com.jeremyrempel.android.chartview.api.BlockChainService
import com.jeremyrempel.android.chartview.presentation.ChartDetailPresenter
import com.jeremyrempel.android.chartview.presentation.ChartDetailRxPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module(includes = [BlockChainApiModule::class])
object ChartDetailModule {

    @Provides
    fun providesPresenter(
        service: BlockChainService,
        @Named("compute")
        schedulerCompute: Scheduler,
        @Named("main")
        schedulerMain: Scheduler
    ): ChartDetailPresenter {
        return ChartDetailRxPresenter(service, schedulerCompute, schedulerMain)
    }
}
