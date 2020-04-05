package com.adcenter.di.dagger.module

import com.adcenter.appconfig.IAppConfig
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.mappers.AppConfigMapper
import com.adcenter.datasource.mappers.DetailsMapper
import com.adcenter.resource.IResourceProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideAdsMapper(appConfig: IAppConfig): AdsMapper = AdsMapper(appConfig)

    @Provides
    fun provideAppConfigMapper(): AppConfigMapper = AppConfigMapper()

    @Provides
    fun provideDetailsMapper(
        appConfig: IAppConfig,
        resourceProvider: IResourceProvider
    ): DetailsMapper = DetailsMapper(appConfig, resourceProvider)
}