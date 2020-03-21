package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideAdsDataProcessor(gson: Gson, api: IApi): AdsDataProcessor =
        AdsDataProcessor(gson, api)
}