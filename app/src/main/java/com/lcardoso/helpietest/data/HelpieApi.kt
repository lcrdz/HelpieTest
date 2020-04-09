package com.lcardoso.helpietest.data

import com.lcardoso.helpietest.util.BASE_URL
import com.lcardoso.helpietest.util.TIME_OUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HelpieApi {

    private val okHttpClient = provideClient()
    private val retrofit = provideRetrofit(okHttpClient)

    val service: HelpieApiService = retrofit.create(HelpieApiService::class.java)

    private fun provideClient() = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

    private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}
