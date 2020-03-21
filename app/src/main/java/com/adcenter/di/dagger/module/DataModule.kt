package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.*
import com.adcenter.resource.IResourceProvider
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

    @Provides
    fun provideDetailsProcessor(
        resourceProvider: IResourceProvider,
        gson: Gson,
        api: IApi
    ): DetailsProcessor =
        DetailsProcessor(resourceProvider, gson, api)

    @Provides
    fun provideAppConfigProcessor(gson: Gson): AppConfigProcessor =
        AppConfigProcessor(gson)

    @Provides
    fun providePhotoProcessor(gson: Gson): PhotoProcessor =
        PhotoProcessor(gson)

    @Provides
    fun provideNewDetailsProcessor(gson: Gson): NewDetailsProcessor =
        NewDetailsProcessor(gson)
}