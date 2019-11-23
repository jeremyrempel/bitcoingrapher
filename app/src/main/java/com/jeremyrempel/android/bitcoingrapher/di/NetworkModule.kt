package com.jeremyrempel.android.bitcoingrapher.di

import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.net.URL
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class CacheDir

@Qualifier
annotation class CacheSizeBytes

@Qualifier
annotation class ApiUrl

@Retention(AnnotationRetention.BINARY)
@Qualifier
private annotation class InternalApi

@Module
object NetworkModule {

    @Provides
    @InternalApi
    fun providesCache(
        @CacheDir cacheDir: File,
        @CacheSizeBytes cacheSizeBytes: Long
    ): Cache {
        return Cache(
            cacheDir,
            cacheSizeBytes
        )
    }

    @Provides
    @InternalApi
    fun provideClient(@InternalApi cache: Cache): OkHttpClient {
        return OkHttpClient
            .Builder()
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@InternalApi client: Lazy<OkHttpClient>, @ApiUrl baseUrl: URL): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call {
                    return client.get().newCall(request)
                }
            })
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
