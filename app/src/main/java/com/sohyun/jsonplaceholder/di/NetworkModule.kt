package com.sohyun.jsonplaceholder.di

import com.sohyun.jsonplaceholder.data.network.PlaceHoldApi
import com.sohyun.jsonplaceholder.data.network.PlaceHoldApi.Companion.PLACE_HOLD_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providePlaceHoldApi(): PlaceHoldApi {
        return Retrofit.Builder()
            .baseUrl(PLACE_HOLD_BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlaceHoldApi::class.java)
    }
}