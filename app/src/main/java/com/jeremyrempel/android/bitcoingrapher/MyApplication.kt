package com.jeremyrempel.android.bitcoingrapher

import android.app.Application
import com.jeremyrempel.android.bitcoingrapher.di.AppComponent
import com.jeremyrempel.android.bitcoingrapher.di.AppModule
import com.jeremyrempel.android.bitcoingrapher.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApplication : Application() {

    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()

        dagger = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
