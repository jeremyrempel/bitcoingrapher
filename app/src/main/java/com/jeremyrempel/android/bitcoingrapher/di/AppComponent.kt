package com.jeremyrempel.android.bitcoingrapher.di

import com.jeremyrempel.android.chartview.di.ChartDetailModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, ChartDetailModule::class])
interface AppComponent {
    @Singleton
    fun fragFactory(): MyFragmentFactory
}
