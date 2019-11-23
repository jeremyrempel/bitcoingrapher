package com.jeremyrempel.android.bitcoingrapher.di

import android.content.Context
import com.jeremyrempel.android.bitcoingrapher.API_URL
import com.jeremyrempel.android.bitcoingrapher.CACHE_SIZE_BYTES
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.net.URL
import javax.inject.Named

@Module
class AppModule(private val context: Context) {

    @Provides
    @CacheDir
    fun providesCacheDir(): File {
        return context.cacheDir
    }

    @Provides
    @ApiUrl
    fun providesUrl(): URL {
        return URL(API_URL)
    }

    @Provides
    @CacheSizeBytes
    fun providesCacheSize(): Long {
        return CACHE_SIZE_BYTES
    }

    @Provides
    @Named("compute")
    fun providesComputeScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @Provides
    @Named("main")
    fun providesMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
